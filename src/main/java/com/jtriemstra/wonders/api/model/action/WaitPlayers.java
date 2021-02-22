package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class WaitPlayers extends Wait {
	
	private Player gameCreator;
	
	public WaitPlayers(Player gameCreator) {
		super(Wait.For.PLAYERS);
		this.gameCreator = gameCreator;
	}
	
	@Override
	public boolean isComplete(Game game) {
		return game.getNumberOfPlayersExpected() == game.getNumberOfPlayers();
	}
	
	@Override
	public void finishWaiting(Game game) {
		gameCreator.addNextAction(new StartGame());
	}
	
	
}
