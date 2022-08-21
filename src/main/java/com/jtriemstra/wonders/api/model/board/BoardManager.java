package com.jtriemstra.wonders.api.model.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.jtriemstra.wonders.api.model.exceptions.BoardInUseException;

import lombok.Getter;

public class BoardManager {

	private BoardSource source;
	private BoardStrategy strategy;
	@Getter
	private BoardSide sides;
	private Set<String> usedBoards;

	public BoardManager(BoardSource source, BoardStrategy strategy, BoardSide sides) {
		this.source = source;
		this.strategy = strategy;
		this.sides = sides;
		this.usedBoards = new HashSet<>();
	}
	
	public Board getBoard() {
		Board b = strategy.getBoard(source, sides, usedBoards);
		return b;
	}
	
	public Board swap(String oldName, String newName, boolean sideA) throws BoardInUseException {
		synchronized (this) {
			if (usedBoards.contains(newName) && !newName.equals(oldName)) {
				throw new BoardInUseException();
			}
			
			usedBoards.remove(oldName);
			usedBoards.add(newName);
			
			return source.getBoards().get(newName).getBoard(sideA);
		}
	}
	
	public Map<String, Boolean> getBoardsInUse(){
		Map<String, Boolean> result = new HashMap<>();
		for (String s : source.getBoards().keySet()) {
			result.put(s, usedBoards.contains(s));
		}
		return result;
	}
}


