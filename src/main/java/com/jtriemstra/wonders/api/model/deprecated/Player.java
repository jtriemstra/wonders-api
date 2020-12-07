package com.jtriemstra.wonders.api.model.deprecated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jtriemstra.wonders.api.model.action.PossibleActions;
import com.jtriemstra.wonders.api.model.action.StartGame;

public class Player {
	private String name;
	
	private ActionList actions;

	public Player(String playerName, ActionList actions) {
		this.name = playerName;
		this.actions = actions;
	}
	
	public String getName() {
		return this.name;
	}
	public void popAction() {
		
	}

	public void addNextAction(StartGame startGame) {
		
	}
	
	public PossibleActions getNextAction() {
		return actions.getNext();
	}
}
