package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgePhase extends Phase {
	private AtomicBoolean isPhaseStarted = new AtomicBoolean();
	public AgePhase(DeckFactory deckFactory, int numberOfPlayers, int age) {
		super(10.0 + age, new GamePhaseStartBasic(deckFactory, numberOfPlayers, age));
		isPhaseStarted.set(false);
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		log.info("checking phase complete, final turn? " + g.isFinalTurn());
		log.info("checking phase complete, actions? " + g.hasPostTurnActions());
		log.info("checking phase complete, boolean " + g.isPhaseStarted());

		return g.isFinalTurn() && !g.hasPostTurnActions() && !g.hasPostGameActions();
	}
	
	@Override
	public void endPhase(Game g) {
		log.info("endPhase");
		g.cleanUpPostTurn();
		g.endAge();
		isPhaseStarted.set(false);
		
		if (!g.isFinalAge()) {
			g.doForEachPlayer(p -> p.addNextAction(new GetEndOfAge()));				
		}	
		else {
			g.doForEachPlayer(p -> p.addNextAction(new GetEndOfGame()));			
		}
	}

	@Override
	public void loopPhase(Game g) {
		log.info("loopPhase");
		g.cleanUpPostTurn();
		
		g.incrementTurn();
		
		g.passCards();
		
		g.doForEachPlayer(p -> p.startTurn());		
	}

	@Override
	public void startPhase(Game g) {
		log.info("startPhase");
		super.startPhase(g);
		isPhaseStarted.set(true);
	}
	@Override
	public boolean phaseStarted() {
		return isPhaseStarted.get();
	}
}
