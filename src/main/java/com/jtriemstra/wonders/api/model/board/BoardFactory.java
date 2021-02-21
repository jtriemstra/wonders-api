package com.jtriemstra.wonders.api.model.board;

import java.util.Random;

import com.jtriemstra.wonders.api.model.Game.BoardSide;

public interface BoardFactory {
	public Board getBoard();

	public void setSideOptions(BoardSide sideOptions);
}
