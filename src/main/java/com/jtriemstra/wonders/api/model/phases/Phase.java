package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Phase {
	private Double order;
	private ActionFactory action;//TODO: can I get rid of actions?
	private GamePhaseStart startFunction;
	private int maxLoops;
	private int currentLoop;
	
	public boolean phaseComplete(Game g) {
		return true;
	}
	
	public void endPhase(Game g) {
		
	}
	
	public void loopPhase(Game g) {
		
	}
}
