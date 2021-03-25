package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AgePhase extends Phase {
	public AgePhase(int age) {
		super(10.0 + age, ()->null, new GamePhaseStartBasic(), 1, 1);
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		log.info("checking phase complete, final turn? " + g.isFinalTurn());
		log.info("checking phase complete, actions? " + g.hasPostTurnActions());
		log.info("checking phase complete, boolean " + g.isAgeStarted());

		return g.isFinalTurn() && !g.hasPostTurnActions();
	}
	
	@Override
	public void endPhase(Game g) {
		g.cleanUpPostTurn();
		
		g.setAgeStarted(false);
		
		if (!g.isFinalAge()) {
			g.doForEachPlayer(p -> p.addNextAction(new GetEndOfAge()));				
		}	
		else {
			g.doForEachPlayer(p -> p.addNextAction(new GetEndOfGame()));			
		}
	}

	@Override
	public void loopPhase(Game g) {
		g.cleanUpPostTurn();
		
		g.incrementTurn();
		
		g.passCards();
		
		g.doForEachPlayer(p -> p.startTurn());		
	}
}
