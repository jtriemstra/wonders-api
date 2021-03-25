package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//TODO: this is not a factory anymore
public class GamePhaseFactoryBasic implements GamePhaseFactory {
	private List<Phase> phases;
	private int currentIndex = -1;
	
	public GamePhaseFactoryBasic() {
		this.phases = new ArrayList<>();
		
		this.phases.add(new AgePhase(1));
		this.phases.add(new AgePhase(2));
		this.phases.add(new AgePhase(3));
	}

	@Override
	public ActionFactory getAction() {
		return this.phases.get(currentIndex).getAction();
	}

	@Override
	public GamePhaseStart getStartFunction() {
		return this.phases.get(currentIndex).getStartFunction();
	}

	@Override
	public void addPhase(double order, ActionFactory action, GamePhaseStart startFunction) {
		
		int i=0;
		while (phases.get(i).getOrder() < order && i < phases.size()) {
			i++;
		}
		
		phases.add(i, new Phase(order, action, startFunction, 1, 1));		
	}

	@Override
	public void nextPhase() {
		/*if (currentIndex >=0 && phases.get(currentIndex).getCurrentLoop() < phases.get(currentIndex).getMaxLoops()) {
			phases.get(currentIndex).setCurrentLoop(1 + phases.get(currentIndex).getCurrentLoop());
		}
		else {*/
			currentIndex++;
			log.info("moving to phase " + currentIndex);
		//}
	}
	
	@Override
	public boolean hasNext() {
		return currentIndex < phases.size() - 1;
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		boolean result = currentIndex < 0 || phases.get(currentIndex).phaseComplete(g);
		log.info("checking phase " + currentIndex + " complete, " + result);
		return result;
	}
	
	@Override
	public void phaseLoop(Game g) {
		if (currentIndex >=0) phases.get(currentIndex).loopPhase(g);
	}
	
	@Override
	public void phaseEnd(Game g) {
		if (currentIndex >=0) phases.get(currentIndex).endPhase(g);
	}

	@Override
	public Phase getCurrentPhase() {
		if (currentIndex >=0) return phases.get(currentIndex);
		return null;
	}
}
