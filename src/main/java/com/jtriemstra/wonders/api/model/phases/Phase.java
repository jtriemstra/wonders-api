package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Phase {
	private Double order;
	
	public boolean phaseComplete(Game g) {
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
}
