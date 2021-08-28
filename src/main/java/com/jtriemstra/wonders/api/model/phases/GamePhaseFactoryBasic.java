package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.DiscardPile;
import com.jtriemstra.wonders.api.model.action.DiscardFinalCardAction;
import com.jtriemstra.wonders.api.model.action.PlayCardsAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.ResolveCommerceAction;
import com.jtriemstra.wonders.api.model.action.ResolveConflictAction;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GamePhaseFactoryBasic implements GamePhaseFactory {
	
	private DeckFactory deckFactory;
	private int numberOfPlayers;
	private PostTurnActionFactoryDefault ptaFactory;
		
	@Override
	public List<Phase> getPhases() {
		
		List<Phase> result = new ArrayList<>();
		
		result.add(new StartingResourceAndCoinsPhase());
		result.add(new AgePhase(deckFactory, numberOfPlayers, 1, ptaFactory.getPostTurnActions(), null));
		result.add(new AgePhase(deckFactory, numberOfPlayers, 2, ptaFactory.getPostTurnActions(), null));
		result.add(new AgePhase(deckFactory, numberOfPlayers, 3, ptaFactory.getPostTurnActions(), new PostTurnActions()));
		
		return result;
	}

}
