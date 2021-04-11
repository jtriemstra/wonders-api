package com.jtriemstra.wonders.api.model.phases;

import java.util.List;

public class GamePhaseFactoryBoard implements GamePhaseFactory {
	
	private GamePhaseFactory innerFactory;
	
	public GamePhaseFactoryBoard(GamePhaseFactory inner) {
		innerFactory = inner;
	}

	@Override
	public List<Phase> getPhases() {
		List<Phase> result = innerFactory.getPhases();
		result.add(new Phase(5.0, new GamePhaseStartBoard()));
		return result;
	}
}
