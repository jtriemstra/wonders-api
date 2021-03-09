package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.action.BaseAction;

public class GamePhaseFactoryBasic implements GamePhaseFactory {
	//TODO: maybe this should be a single list of phases
	private List<Double> orders;
	private List<ActionFactory> actions;
	private List<GamePhaseStart> startFunctions;
	private int currentIndex = -1;
	
	public GamePhaseFactoryBasic() {
		this.orders = new ArrayList<>();
		this.actions = new ArrayList<>();
		this.startFunctions = new ArrayList<>();
		
		this.orders.add(10.0);
		this.actions.add(() -> null);
		this.startFunctions.add(new GamePhaseStartBasic());
		
	}

	@Override
	public ActionFactory getAction() {
		return this.actions.get(currentIndex);
	}

	@Override
	public GamePhaseStart getStartFunction() {
		return this.startFunctions.get(currentIndex);
	}

	@Override
	public void addPhase(double order, ActionFactory action, GamePhaseStart startFunction) {
		
		int i=0;
		while (orders.get(i) < order && i < orders.size()) {
			i++;
		}
		
		orders.add(i, order);
		actions.add(i, action);
		startFunctions.add(i, startFunction);
	}

	@Override
	public void nextPhase() {
		currentIndex++;
	}
	
	@Override
	public boolean hasNext() {
		return currentIndex < orders.size() - 1;
	}
}
