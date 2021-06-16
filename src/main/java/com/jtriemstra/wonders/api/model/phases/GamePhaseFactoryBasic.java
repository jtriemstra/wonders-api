package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.deck.DeckFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GamePhaseFactoryBasic implements GamePhaseFactory {
	
	DeckFactory deckFactory;
	int numberOfPlayers;
	
	@Override
	public List<Phase> getPhases() {
		List<Phase> result = new ArrayList<>();
		
		result.add(new StartingResourceAndCoinsPhase());
		result.add(new AgePhase(deckFactory, numberOfPlayers, 1));
		result.add(new AgePhase(deckFactory, numberOfPlayers, 2));
		result.add(new AgePhase(deckFactory, numberOfPlayers, 3));
		
		return result;
	}

}
