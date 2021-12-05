package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Phase {
	private Double order;
	
	public boolean phaseComplete() {
		return true;
	}

	public boolean phaseStarted() {
		return false;
	}
	
	public void endPhase(Game g) {
		
	}
	
	public void loopPhase(Game g) {
		
	}
	
	public void startPhase(Game g) {
		
	}

	public void addPostTurnAction(IPlayer p, PostTurnAction action) {
		
	}
	
	public void addPostGameAction(IPlayer p, PostTurnAction action) {
		
	}
	
	public void injectPostTurnAction(IPlayer p, PostTurnAction action, int additionalIndex) {
		
	}
	
	public void handlePostTurnActions(Game g) {
		
	}
}

