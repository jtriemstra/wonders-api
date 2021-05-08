package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChooseLeaderPhase extends Phase {
	private AtomicBoolean isPhaseStarted = new AtomicBoolean();
	public ChooseLeaderPhase(int age) {
		super(9.5 + age, new GamePhaseStartChooseLeader());
		isPhaseStarted.set(false);
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		return true;
	}
	
	@Override
	public void endPhase(Game g) {
		log.info("endPhase");
		g.doForEachPlayer(p -> p.clearHand());
		
		g.cleanUpPostTurn();
		
		isPhaseStarted.set(false);
		
	}

	@Override
	public void loopPhase(Game g) {
		log.info("loopPhase");	
	}

	@Override
	public void startPhase(Game g) {
		log.info("startPhase");
		isPhaseStarted.set(true);
		super.startPhase(g);
	}
	@Override
	public boolean phaseStarted() {
		return isPhaseStarted.get();
	}
}
