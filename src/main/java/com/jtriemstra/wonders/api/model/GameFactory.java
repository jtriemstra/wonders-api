package com.jtriemstra.wonders.api.model;

import com.jtriemstra.wonders.api.model.board.BoardSide;

public interface GameFactory {
	public Game createGame(String name, int numberOfPlayers, boolean isLeaders, BoardSide sideOption, boolean chooseBoard);
}
