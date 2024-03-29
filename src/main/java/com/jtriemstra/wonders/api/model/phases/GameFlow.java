package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameFlow  {
	@JsonProperty("phases")
	private List<Phase> phases = new ArrayList<>();
	@JsonProperty("currentIndex")
	private int currentIndex = -1;
	
	public GameFlow(GamePhaseFactory phaseFactory) {
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
	
	public boolean phaseComplete() {
		return currentIndex < 0 || phases.get(currentIndex).phaseComplete();		
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
	

	//TODO: avoid these casts...these largely exist to support PostTurnActions, and if we set that up to be turn-specific instead of re-using 
	// the same object 
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
	
	public void addPostTurnAction(IPlayer p, PostTurnAction action, PhaseMatcher matcher) {
		for (Phase phase : phases) {
			if (matcher.matches(phase, this)) {
				phase.addPostTurnAction(p, action);
			}
		}
	}
	
	public void injectPostTurnAction(IPlayer p, PostTurnAction action, int additionalIndex, PhaseMatcher matcher) {
		for (Phase phase : phases) {
			if (matcher.matches(phase, this)) {
				phase.injectPostTurnAction(p, action, additionalIndex);
			}
		}
	}

	public void addPostGameAction(IPlayer p, PostTurnAction action, Class phaseClazz) {
		//TODO: it's sort of meaningless, but logically this should only get added to the last phase
		for (Phase phase : phases) {
			if (phaseClazz.isInstance(phase)) {
				phase.addPostGameAction(p, action);
			}
		}
	}
	
}
