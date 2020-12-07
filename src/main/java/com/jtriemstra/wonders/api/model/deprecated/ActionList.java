package com.jtriemstra.wonders.api.model.deprecated;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jtriemstra.wonders.api.model.action.PossibleActions;

public class ActionList {
	private List<PossibleActions> actions = new ArrayList<>();
	
	public PossibleActions getNext() {
		if (actions.size() == 0) {
			throw new RuntimeException("tried to get next action, but there are zero actions available");
		}
		
		return actions.get(actions.size() - 1);
	}
}
