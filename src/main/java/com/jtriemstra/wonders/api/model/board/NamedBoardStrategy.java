package com.jtriemstra.wonders.api.model.board;

import java.util.Map;
import java.util.Set;

public class NamedBoardStrategy implements BoardStrategy {
	
	private String boardNames;
	private int numberCreated;
	private Map<String, BoardFactoryMethod> boardCreators;
	
	public NamedBoardStrategy(String boardNames) {
		this.boardNames = boardNames;
		this.numberCreated = 0;
	}
	
	@Override
	public Board getBoard(BoardSource source, BoardSide sides, Set<String> usedBoards) {
		if (boardCreators == null) {
			this.boardCreators = source.getBoards();
		}
		
		String[] namesAndSides = boardNames.split(";");
		String s = namesAndSides[numberCreated];
		boolean sideA = s.split("-")[1].equals("A") ? true : false;
		
		numberCreated++;
		
		if (boardCreators.containsKey(s.split("-")[0])) {
			usedBoards.add(s.split("-")[0]);
			return boardCreators.get(s.split("-")[0]).getBoard(sideA);
		}
		else {
			throw new RuntimeException("board not found");
		}		
	}
}
