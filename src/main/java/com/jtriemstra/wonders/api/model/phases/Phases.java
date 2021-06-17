package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Phases  {
	private List<Phase> phases = new ArrayList<>();
	private int currentIndex = -1;
	
	public Phases(GamePhaseFactory phaseFactory) {
		phaseFactory.getPhases().forEach(p -> addPhase(p));
	}

	private void addPhase(Phase p) {
		//TODO: (low) there's probably an ordered data structure I can use
		int i=0;

		while (i < phases.size() && phases.get(i).getOrder() < p.getOrder()) {
			i++;
		}

		phases.add(i, p);		
	}

	public void nextPhase() {
		currentIndex++;
		log.info("moving to phase " + currentIndex);
	}
	
	public boolean hasNext() {
		return currentIndex < phases.size() - 1;
	}
	
	public boolean phaseComplete(Game g) {
		boolean result = currentIndex < 0 || phases.get(currentIndex).phaseComplete(g);
		log.info("checking phase " + currentIndex + " complete, " + result);
		return result;
	}
	
	public void phaseLoop(Game g) {
		if (currentIndex >=0) phases.get(currentIndex).loopPhase(g);
	}
	
	public void phaseEnd(Game g) {
		if (currentIndex >=0) phases.get(currentIndex).endPhase(g);
	}

	public void phaseStart(Game g) {
		if (currentIndex >=0) phases.get(currentIndex).startPhase(g);
	}
	
	public Phase getCurrentPhase() {
		if (currentIndex >=0) return phases.get(currentIndex);
		return null;
	}

	public boolean isPhaseStarted() {
		if (currentIndex >=0) return phases.get(currentIndex).phaseStarted();
		return false;
	}
}
