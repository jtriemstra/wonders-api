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
		result.add(new Phase(5.0, () -> null, new GamePhaseStartBoard(), 1, 1));
		return result;
	}
}
