package com.jtriemstra.wonders.api.model.board;

import java.util.HashMap;
import java.util.Map;

import com.jtriemstra.wonders.api.model.exceptions.BoardInUseException;

public class ChooseBoardFactory extends RandomBoardFactory implements BoardFactory {
	// returns true if swap is successful, else false
	public Board swap(int oldId, int newId, boolean sideA) throws BoardInUseException {
		synchronized (this) {
			if (usedBoards.contains(newId)) {
				throw new BoardInUseException();
			}
			
			usedBoards.remove(oldId);
			usedBoards.add(newId);
			
			return getBoard(sideA, newId);
		}
	}
	
	public Map<String, Boolean> getBoardsInUse(){
		Map<String, Boolean> result = new HashMap<>();
		for (int i=0; i<7; i++) {
			result.put(Board.getName(i), usedBoards.contains(i));
		}
		return result;
	}
}
