package com.jtriemstra.wonders.api.model.board;

import java.util.Random;
import java.util.Set;

public class RandomBoardStrategy implements BoardStrategy {

	@Override
	public Board getBoard(BoardSource source, BoardSide sides, Set<String> usedBoards) {
		if (usedBoards.size() == source.getBoards().size()) throw new RuntimeException("all boards have been used");
		
		Random r = new Random();
		boolean sideA;
		switch (sides) {
		case A_OR_B: sideA = r.nextBoolean(); break;
		case A_ONLY: sideA = true; break;
		case B_ONLY: sideA = false; break;
		default: throw new RuntimeException("unknown boardside option");
		}
		
		int boardIndex = r.nextInt(source.getBoards().size() - 1);
		
		String[] boardNames = new String[source.getBoards().size()];
		source.getBoards().keySet().toArray(boardNames);
		
		//TODO: is this OK to synchronize on if there are multiple simultaneous games? Maybe should pass reference to game and use that to be safe
		synchronized (this) {
			while (usedBoards.contains(boardNames[boardIndex])) boardIndex = r.nextInt(source.getBoards().size() - 1);
			usedBoards.add(boardNames[boardIndex]);
			return source.getBoards().get(boardNames[boardIndex]).getBoard(sideA);
		}
	}

}
