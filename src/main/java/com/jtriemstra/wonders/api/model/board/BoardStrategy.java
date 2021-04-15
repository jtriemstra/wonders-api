package com.jtriemstra.wonders.api.model.board;

import java.util.Set;

public interface BoardStrategy {
	public Board getBoard(BoardSource source, BoardSide sides, Set<String> usedBoards);
}
