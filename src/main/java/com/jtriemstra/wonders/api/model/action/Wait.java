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
				boolean isFirstCall = game.allWaiting(); // this assumes something is going to change actions. true?
				
				if (game.hasNextPhase()) {
					if (isFirstCall) {
						game.startNextPhase();
					}
					
					BaseAction nextAction = game.nextPhaseAction();
					if (nextAction != null) player.addNextAction(nextAction);
				}
				else {
					if (isFirstCall) {
						finishWaiting(game);
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
		BOARDS //TODO: this probably doesn't scale to expansions
	}
}
