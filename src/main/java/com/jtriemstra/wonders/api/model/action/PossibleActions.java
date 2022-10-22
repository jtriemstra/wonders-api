package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PossibleActions {
	@JsonProperty("actions")
	private List<BaseAction> actions;

	public PossibleActions(BaseAction... a) {
		actions = new ArrayList<BaseAction>();
		for (BaseAction ba : a) {
			actions.add(ba);
		}
	}

	public BaseAction getByName(String actionName) {
		for (BaseAction a : actions) {
			if (a.getName().equals(actionName)) {
				return a;
			}
		}
		
		//This is to handle cases where the browser thinks the next action is wait, but something has changed on the server. Wait is still a valid call, and the return data will pick up the new next action.
		if ("wait".equals(actionName)) {
			return new WaitTurn();
		}
		
		throw new RuntimeException("action " + actionName + " not currently available");
	}	
	
	public void removeAction(String name) {
		int matchingIndex = -1;
		for (int i=0; i<actions.size(); i++) {
			if (name.equals(actions.get(i).getName())) {
				matchingIndex = i;
			}
		}
		
		Assert.isTrue(matchingIndex > -1, "action not currently available");
				
		actions.remove(matchingIndex);
	}
	
	@Override
	public String toString() {
		String result = "";
		if (actions != null) {
			for (int i=0; i<actions.size(); i++) {
				if (i > 0) result += ";";
				result += actions.get(i).getName();
			}	
		}
		return result;
	}

	public boolean includes(String actionName) {
		for (BaseAction a : actions) {
			if (a.getName().equals(actionName)) {
				return true;
			}
		}
		
		return false;
	}

	public Object[] getOptions() {
		for (BaseAction a : actions) {
			if (a instanceof OptionAction) {
				return ((OptionAction) a).getOptions();
			}
		}
		
		return null;
	}
}
