package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Wait implements BaseAction {
	
	private For waitFor;
	
	protected Wait(For waitFor) {
		this.waitFor = waitFor;
	}
	
	@Override
	public String getName() {
		return "wait";
	}
	
	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		log.info("wait.execute for " + player.getName());
		synchronized(game) {

			if (isComplete(game)) {
				log.info("wait complete");
				boolean isFirstCall = game.allWaiting(); // this assumes something is going to change actions, so subsequent calls will not repeat this block. true?
				
				if (isFirstCall) {
					//TODO: this could be fragile, if something unexpected gets added in a Wait subclass
					finishWaiting(game); 
					
					if (game.allWaiting()) { // finishWaiting() could have introduced new options actions to respond to, so re-check allWaiting()
						if (game.isPhaseStarted() && !game.phaseComplete()) {
							log.info("phase not complete, looping");
							game.phaseLoop();
						}
						else if (game.isPhaseStarted()) {
							log.info("phase complete");
							game.phaseEnd(); 
						}
						else if (game.hasNextPhase()) {
							log.info("wait has next phase");
							game.startNextPhase();
						}
						else {
							
						}
					}
				}
			}
		}
		
		WaitResponse r = new WaitResponse();
		r.setWaitFor(waitFor);
		
		return r;
	}
	
	public boolean isComplete(Game game) {
		return false;
	}
	
	public void finishWaiting(Game game) {
		
	}
	
	public enum For {
		START,
		PLAYERS,
		TURN, 
		NULL, 
		BOARDS 
	}
}
