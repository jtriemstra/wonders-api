package com.jtriemstra.wonders.api.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class Ages {
	private int currentAge = 0;
	private int currentTurn = 1;
	
	public boolean finishTurnAndCheckEndOfAge() {
		currentTurn += 1;
		if (currentTurn == 7) {
			return true;
		}
		
		return false;
	}
	
	public boolean incrementAge() {
		if (currentAge == 3) return false;
		
		currentAge += 1;
		currentTurn = 1;
		return true;
		
	}

	public int getCurrentAge() {
		return currentAge;
	}

	public boolean isFinalTurn() {
		return currentTurn == 6;
	}

	public boolean isFinalAge() {
		return currentAge == 3;
	}

	public boolean passClockwise() {
		return currentAge == 1 || currentAge == 3;
	}
}
