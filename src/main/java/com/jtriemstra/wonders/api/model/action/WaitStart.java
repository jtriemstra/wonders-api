package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.Game;

//TODO: can probably remove this
public class WaitStart extends Wait {
	public WaitStart() {
		super(Wait.For.START);
	}
	
	@Override
	public boolean isComplete(Game game) {
		return game.getCurrentAge() > 0;
	}
	
}
