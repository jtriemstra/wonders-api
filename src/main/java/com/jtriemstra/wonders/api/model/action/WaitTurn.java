package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.Game;

public class WaitTurn extends Wait {
	public WaitTurn() {
		super(Wait.For.TURN);
	}	

	@Override
	public void finishWaiting(Game game) {
		game.handlePostTurnActions();
	}
	
	@Override
	public boolean isComplete(Game game) {
		return game.allWaiting();
	}
}
