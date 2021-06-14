package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.deck.Deck;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GamePhaseStartBasic implements GamePhaseStart {

	private DeckFactory deckFactory;
	private int numberOfPlayers;
	private int age;
	
	@Override
	public void start(Game g) {
		log.info("GamePhaseStartBasic");
		
		g.doForEachPlayer(p -> {
			log.info("adding WaitTurn to " + p.getName());
			
			// not sure this is necessary, but just sits on the queue as a fallback next action.
			p.addNextAction(new WaitTurn());
		});
		
		g.startAge();
		
		Deck deck = deckFactory.getDeck(numberOfPlayers, age);
		
		for (int i=0; i<7; i++) {
			g.doForEachPlayer(p -> {
				p.receiveCard(deck.draw());
			});		
		}
		
		g.doForEachPlayer(p -> {
			p.startTurn();
		});
	}

}
