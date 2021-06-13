package com.jtriemstra.wonders.api.model.phases;

import java.util.List;

import com.jtriemstra.wonders.api.model.board.BoardManager;

public class GamePhaseFactoryBoard implements GamePhaseFactory {
	
	private GamePhaseFactory innerFactory;
	private BoardManager boardManager;
	
	public GamePhaseFactoryBoard(GamePhaseFactory inner, BoardManager boardManager) {
		innerFactory = inner;
		this.boardManager = boardManager;
	}

	@Override
	public List<Phase> getPhases() {
		List<Phase> result = innerFactory.getPhases();
		result.add(new Phase(5.0, new GamePhaseStartBoard(boardManager)));
		return result;
	}
}
