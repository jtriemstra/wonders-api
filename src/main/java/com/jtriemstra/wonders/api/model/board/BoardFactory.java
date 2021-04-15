package com.jtriemstra.wonders.api.model.board;

import java.util.Random;

public interface BoardFactory {
	public Board getBoard();

	public void setSideOptions(BoardSide sideOptions);
}
