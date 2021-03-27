package com.jtriemstra.wonders.api.model.phases;

import java.util.List;

public class GamePhaseFactoryLeader implements GamePhaseFactory {
	
	private GamePhaseFactory innerFactory;
	
	public GamePhaseFactoryLeader(GamePhaseFactory inner) {
		innerFactory = inner;
	}

	@Override
	public List<Phase> getPhases() {
		List<Phase> result = innerFactory.getPhases();
		result.add(new LeaderPhase());
		result.add(new ChooseLeaderPhase(1));
		result.add(new ChooseLeaderPhase(2));
		result.add(new ChooseLeaderPhase(3));
		return result;
	}
}
