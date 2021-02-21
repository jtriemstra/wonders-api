package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class Wait implements BaseAction {
	
	private For waitFor;
	
	public Wait(For waitFor) {
		this.waitFor = waitFor;
	}
	
	@Override
	public String getName() {
		return "wait";
	}
	
	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		if (game.notifyWaiting(waitFor, this)) {
			player.popAction();
			finishWaiting();
		}
		
		WaitResponse r = new WaitResponse();
		r.setWaitFor(waitFor);
		
		return r;
	}
	
	public boolean isComplete(Game game) {
		return false;
	}
	
	public void finishWaiting() {
		
	}
	
	public enum For {
		START,
		PLAYERS,
		TURN, NULL
	}
}
