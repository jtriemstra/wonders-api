package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.state.StateService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GamePhaseFactoryBasic implements GamePhaseFactory {
	
	private DeckFactory deckFactory;
	private int numberOfPlayers;
	private PostTurnActionFactoryDefault ptaFactory;
	private StateService stateService;
		
	@Override
	public List<Phase> getPhases() {
		
		List<Phase> result = new ArrayList<>();
		
		result.add(new StartingResourceAndCoinsPhase());
		result.add(new AgePhase(deckFactory, numberOfPlayers, 1, ptaFactory.getPostTurnActions(), null));
		result.add(new AgePhase(deckFactory, numberOfPlayers, 2, ptaFactory.getPostTurnActions(), null));
		result.add(new AgePhase(deckFactory, numberOfPlayers, 3, ptaFactory.getPostTurnActions(), new PostTurnActions(stateService)));
		
		return result;
	}

}
