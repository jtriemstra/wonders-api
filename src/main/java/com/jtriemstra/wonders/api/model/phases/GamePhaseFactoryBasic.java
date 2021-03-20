package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.action.StartAge;

public class GamePhaseFactoryBasic implements GamePhaseFactory {
	private List<Phase> phases;
	private int currentIndex = -1;
	
	public GamePhaseFactoryBasic() {
		this.phases = new ArrayList<>();
		
		this.phases.add(new Phase(10.0, ()->null, new GamePhaseStartBasic(), 3, 1));		
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
		if (currentIndex >=0 && phases.get(currentIndex).getCurrentLoop() < phases.get(currentIndex).getMaxLoops()) {
			phases.get(currentIndex).setCurrentLoop(1 + phases.get(currentIndex).getCurrentLoop());
		}
		else {
			currentIndex++;
		}
	}
	
	@Override
	public boolean hasNext() {
		return currentIndex < phases.size() - 1;
	}
}
