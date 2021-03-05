package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.Game;

public class WaitBoards extends Wait {
	public WaitBoards() {
		super(Wait.For.BOARDS);
	}
	
	@Override
	public boolean isComplete(Game game) {
		return game.allWaiting();
	}
	
}
