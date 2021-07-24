package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.deck.Deck;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgePhase extends Phase {
	private AtomicBoolean isPhaseStarted = new AtomicBoolean();
	
	private DeckFactory deckFactory;
	private int numberOfPlayers;
	@Getter
	private int age;
	private int turn;
	
	public AgePhase(DeckFactory deckFactory, int numberOfPlayers, int age) {
		super(10.0 + age);
		this.isPhaseStarted.set(false);
		this.deckFactory = deckFactory;
		this.numberOfPlayers = numberOfPlayers;
		this.age = age;
		this.turn = 1;
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		return isFinalTurn() && !g.hasPostTurnActions() && !g.hasPostGameActions();
	}
	
	@Override
	public void endPhase(Game g) {
		g.cleanUpPostTurn();
		isPhaseStarted.set(false);
		
		g.doForEachPlayer(p -> p.addNextAction(isFinalAge() ? new GetEndOfGame() : new GetEndOfAge()));		
	}

	@Override
	public void loopPhase(Game g) {
		g.cleanUpPostTurn();		
		incrementTurn();		
		g.passCards(age != 2);		
		g.doForEachPlayer(p -> p.startTurn());		
	}

	@Override
	public void startPhase(Game g) {
		
		g.doForEachPlayer(p -> {
			// not sure this is necessary, but just sits on the queue as a fallback next action.
			p.addNextAction(new WaitTurn());
		});
		
		Deck deck = deckFactory.getDeck(numberOfPlayers, age);
		
		for (int i=0; i<7; i++) {
			g.doForEachPlayer(p -> { p.receiveCard(deck.draw()); });		
		}
		
		g.doForEachPlayer(p -> { p.startTurn();	});
		
		isPhaseStarted.set(true);
	}
	
	@Override
	public boolean phaseStarted() {
		return isPhaseStarted.get();
	}
	
	public boolean isFinalAge() {
		return age == 3;
	}
	
	public boolean isFinalTurn() {
		return turn >= 6;
	}
	
	private void incrementTurn() {
		turn++;
	}
}
