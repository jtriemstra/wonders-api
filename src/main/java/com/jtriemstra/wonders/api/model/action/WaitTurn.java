package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.phases.AgePhase;
import com.jtriemstra.wonders.api.model.phases.ChooseLeaderPhase;
import com.jtriemstra.wonders.api.model.phases.Phase;

public class WaitTurn extends Wait {
		
	public WaitTurn() {
		super(Wait.For.TURN);		
	}	

	@Override
	public void finishWaiting(Game game) {
		Phase currentPhase = game.getCurrentPhase();
		currentPhase.handlePostTurnActions(game);
	}
	
	@Override
	public boolean isComplete(Game game) {
		return game.allWaiting();
	}
}
