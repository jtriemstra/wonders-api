package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

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
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		log.info("wait.execute for " + player.getName());
		synchronized(game) {
			//TODO: clean up this conditional. Also, would this be cleaner in game.doAction()? cleaning up may entail removing the "baseline" wait for turn that currently sits around
			if (isComplete(game)) {
				log.info("wait complete");
				boolean isFirstCall = game.allWaiting(); // this assumes something is going to change actions. true?
				
				if (isFirstCall) {
					log.info("is first call in wait");
					if (game.hasNextPhase()) {
					//if (waitFor != Wait.For.TURN) { //TODO: not sure which form of this condition is more viable long-term. ideally probably neither.
						log.info("wait has next phase");
						game.startNextPhase();
					}
					else {
						finishWaiting(game);
					}
				}
				
				/*if (game.hasNextPhase()) {
				//if (waitFor != Wait.For.TURN) { //TODO: not sure which form of this condition is more viable long-term. ideally probably neither. 
					log.info("wait has next phase");
					if (isFirstCall) {
						log.info("starting phase");
						game.startNextPhase();
					}
					
//					BaseAction nextAction = game.nextPhaseAction(); //TODO: this fails if the first call to wait increments the phase, but subsequent ones still need to pick up the action.
//					if (nextAction != null) player.addNextAction(nextAction);
				}
				else {
					if (isFirstCall) {
						finishWaiting(game);
					}
				}*/
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
		BOARDS //TODO: this probably doesn't scale to expansions
	}
}
