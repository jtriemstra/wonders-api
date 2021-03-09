package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

public class ActionList {
	private List<PossibleActions> actions = new ArrayList<>();
	
	public PossibleActions getNext() {
		if (actions.size() == 0) {
			throw new RuntimeException("tried to get next action, but there are zero actions available");
		}
		
		return actions.get(actions.size() - 1);
	}
	
	public void push(BaseAction... a) {
		actions.add(new PossibleActions(a));
	}
	
	public BaseAction getCurrentByName(String actionName) {
		return actions.get(actions.size() - 1).getByName(actionName);
	}

	public PossibleActions pop() {
		return actions.remove(actions.size() - 1);
	}

	public int size() {
		return actions.size();
	}
}
