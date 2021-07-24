package com.jtriemstra.wonders.api.model.phases;

import java.util.List;

import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GamePhaseFactoryLeader implements GamePhaseFactory {
	
	private GamePhaseFactory innerFactory;
	private LeaderDeck deck;
	private PostTurnActions postTurnActions;
	
	@Override
	public List<Phase> getPhases() {
		List<Phase> result = innerFactory.getPhases();
		result.add(new LeaderPhase(deck));
		result.add(new ChooseLeaderPhase(1));
		result.add(new ChooseLeaderPhase(2));
		result.add(new ChooseLeaderPhase(3));
		return result;
	}
}
