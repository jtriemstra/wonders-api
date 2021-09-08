package com.jtriemstra.wonders.api.model.board;

import java.util.Map;
import java.util.Set;

public class NamedBoardStrategy implements BoardStrategy {
	
	private String boardNames;
	
	public NamedBoardStrategy(String boardNames) {
		this.boardNames = boardNames;
	}
	
	@Override
	public Board getBoard(BoardSource source, BoardSide sides, Set<String> usedBoards) {
		
		String[] namesAndSides = boardNames.split(";");
		String s = namesAndSides[usedBoards.size()];
		boolean sideA = s.split("-")[1].equals("A") ? true : false;
				
		if (source.getBoards().containsKey(s.split("-")[0])) {
			usedBoards.add(s.split("-")[0]);
			return source.getBoards().get(s.split("-")[0]).getBoard(sideA);
		}
		else {
			throw new RuntimeException("board not found");
		}		
	}
}
