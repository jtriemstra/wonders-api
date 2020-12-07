package com.jtriemstra.wonders.api.model;

import com.jtriemstra.wonders.api.model.board.BoardFactory;

//@Component
public interface GameFactory {
	/*@Lookup
	public Game createGame(String gameName) {
		return new Game(gameName);
	}*/
	public Game createGame(String name, BoardFactory boardFactory);
}
