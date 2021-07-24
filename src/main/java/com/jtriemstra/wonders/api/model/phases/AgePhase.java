package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
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
	private PostTurnActions postTurnActions;
	private PostTurnActions postGameActions;
	
	public AgePhase(DeckFactory deckFactory, int numberOfPlayers, int age, PostTurnActions postTurnActions, PostTurnActions postGameActions) {
		super(10.0 + age);
		this.isPhaseStarted.set(false);
		this.deckFactory = deckFactory;
		this.numberOfPlayers = numberOfPlayers;
		this.age = age;
		this.turn = 1;
		this.postTurnActions = postTurnActions;
		this.postGameActions = postGameActions;
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		return isFinalTurn() && !postTurnActions.hasNext() && !postGameActions.hasNext();
	}
	
	@Override
	public void endPhase(Game g) {
		postTurnActions.cleanUp();
		isPhaseStarted.set(false);
		
		g.doForEachPlayer(p -> p.addNextAction(isFinalAge() ? new GetEndOfGame() : new GetEndOfAge()));		
	}

	@Override
	public void loopPhase(Game g) {
		postTurnActions.cleanUp();		
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
	
	public void handlePostTurnActions() {
		if (!phaseStarted()) {
			return;
		}
		//TODO: (low) the post game actions could be done simultaneously, rather than sequentially
		if (postTurnActions.hasNext()) {
			postTurnActions.doNext();
		}
		else {
			if (isFinalAge() && isFinalTurn() && postGameActions.hasNext()) {
				postGameActions.doNext();						
			}
		}		
	}
}
