package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Phases  {
	private List<Phase> phases = new ArrayList<>();
	private int currentIndex = -1;
	
	public Phases(GamePhaseFactory phaseFactory) {
		phaseFactory.getPhases().forEach( p -> addPhase(p) );
	}

	private void addPhase(Phase p) {
		int i=0;

		while (i < phases.size() && phases.get(i).getOrder() < p.getOrder()) { i++; }

		phases.add(i, p);		
	}

	public void nextPhase() {
		currentIndex++;
	}
	
	public boolean hasNext() {
		return currentIndex < phases.size() - 1;
	}
	
	public boolean phaseComplete(Game g) {
		return currentIndex < 0 || phases.get(currentIndex).phaseComplete(g);		
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
	

	//TODO: avoid these casts...maybe can push into AgePhase somewhere
	public boolean isFinalTurn() {
		if (getCurrentPhase() instanceof AgePhase) {
			return ((AgePhase) getCurrentPhase()).isFinalTurn();
		}
		return false;
	}

	public boolean isFinalAge() {
		if (getCurrentPhase() instanceof AgePhase) {
			return ((AgePhase) getCurrentPhase()).isFinalAge();
		}
		return false;
	}

	public int getCurrentAge() {
		if (getCurrentPhase() instanceof AgePhase) {
			return ((AgePhase) getCurrentPhase()).getAge();
		}
		return 0;
	}
	
	public boolean isAgeStarted() {
		if (getCurrentPhase() instanceof AgePhase) {
			return isPhaseStarted();
		}
		return false;
	}
}
