package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.jtriemstra.wonders.api.model.action.Wait.For;


public class PossibleActions {
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
		
		//TODO: (low) don't love this design. This accomodates a situation where a player is waiting, and a new action has been added to their stack (eg to defend or to start a new turn)
		if ("wait".equals(actionName)) {
			return new Wait(For.TURN);
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
