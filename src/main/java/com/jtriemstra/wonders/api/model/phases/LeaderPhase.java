package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeaderPhase extends Phase {
	public LeaderPhase() {
		super(7.0, ()->null, new GamePhaseStartLeader(), 1, 1);
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		log.info("checking leader phase complete, final turn? " + g.isFinalTurn());
		log.info("checking leader phase complete, actions? " + g.hasPostTurnActions());
		log.info("checking leader phase complete, boolean " + g.isAgeStarted());

		return g.isFinalTurn() && !g.hasPostTurnActions();
	}
	
	@Override
	public void endPhase(Game g) {
		
	}

	@Override
	public void loopPhase(Game g) {
		g.passCards();
		
		g.doForEachPlayer(p -> p.startTurn());		
	}
}
