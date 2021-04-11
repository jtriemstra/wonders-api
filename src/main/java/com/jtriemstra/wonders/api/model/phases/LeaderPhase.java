package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.GetOptionsLeaders;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LeaderPhase extends Phase {
	private AtomicBoolean isPhaseStarted = new AtomicBoolean();
	private int loopCounter = 1;
	
	public LeaderPhase() {
		super(7.0, new GamePhaseStartLeader());
		isPhaseStarted.set(false);
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		log.info("checking leader phase complete, loop count " + loopCounter);

		return loopCounter >= 4;
	}
	
	@Override
	public void endPhase(Game g) {
		log.info("endPhase");
		isPhaseStarted.set(false);
	}

	@Override
	public void loopPhase(Game g) {
		log.info("loopPhase");
		g.passCards();
		
		loopCounter++;
		
		//g.doForEachPlayer(p -> p.startTurn());	
		g.doForEachPlayer(p -> {
			log.info("adding GetOptionsLeaders to " + p.getName());
			p.addNextAction(new GetOptionsLeaders());
		});	
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
