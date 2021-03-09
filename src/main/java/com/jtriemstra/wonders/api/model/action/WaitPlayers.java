package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.Game;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaitPlayers extends Wait {
	
	public WaitPlayers() {
		super(Wait.For.PLAYERS);
	}
	
	@Override
	public boolean isComplete(Game game) {
		return game.getNumberOfPlayersExpected() == game.getNumberOfPlayers();
	}
	
}
