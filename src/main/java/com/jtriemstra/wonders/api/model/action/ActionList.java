package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionList {
	@JsonProperty("actions")
	private List<PossibleActions> actions = new ArrayList<>();
	
	@JsonIgnore
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
