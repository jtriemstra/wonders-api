package com.jtriemstra.wonders.api.model.board;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.jtriemstra.wonders.api.model.Game.BoardSide;

public class RandomBoardFactory implements BoardFactory {
	
	protected Set<Integer> usedBoards = new HashSet<>();
	protected BoardSide sideOption = BoardSide.A_OR_B;
	
	@Override
	public Board getBoard() {
		if (usedBoards.size() == 7) throw new RuntimeException("all boards have been used");
		
		Random r = new Random();
		boolean sideA;
		switch (sideOption) {
		case A_OR_B: sideA = r.nextBoolean(); break;
		case A_ONLY: sideA = true; break;
		case B_ONLY: sideA = false; break;
		default: throw new RuntimeException("unknown boardside option");
		}
		
		int boardId = r.nextInt(6);
		
		//TODO: is this OK to synchronize on if there are multiple simultaneous games?
		synchronized (this) {
			while (usedBoards.contains(boardId)) boardId = r.nextInt(6);
			usedBoards.add(boardId);
			return getBoard(sideA, boardId);
		}
	}
	
	protected Board getBoard(boolean sideA, int boardId) {
		switch (boardId) {
		case Board.EPHESUS_ID:	return new Ephesus(sideA);
		case Board.OLYMPUS_ID:	return new Olympia(sideA);
		case Board.HALIKARNASSOS_ID:	return new Halikarnassos(sideA);
		case Board.GIZA_ID: return new Giza(sideA);
		case Board.RHODES_ID: return new Rhodes(sideA);
		case Board.ALEXANDRIA_ID: return new Alexandria(sideA);
		case Board.BABYLON_ID: return new Babylon(sideA);
		default: throw new RuntimeException("board not found");
		}
	}

	@Override
	public void setSideOptions(BoardSide sideOptions) {
		this.sideOption = sideOptions;
	}
}
