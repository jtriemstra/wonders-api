package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class ResolveConflictAction implements NonPlayerAction, PostTurnAction {
	@Override
	public double getOrder() {
		return 2;
	}

	@Override
	public String getName() {
		return "resolveConflict";
	}

	@Override
	public ActionResponse execute(Game game) {
		
		if (!game.isAgeStarted() || !game.isFinalTurn()) {
			return null;
		}

		int thisAgeVictory;
		if (game.getCurrentAge() == 1) thisAgeVictory = 1;
		else if (game.getCurrentAge() == 2) thisAgeVictory = 3;
		else if (game.getCurrentAge() == 3) thisAgeVictory = 5;
		else throw new RuntimeException("invalid age found");
		
		game.doForEachPlayer(p -> {
			Player left = game.getLeftOf(p);
			
			int myArmy = p.getArmies();
			int leftArmy = left.getArmies();
			
			if (myArmy < leftArmy) {
				p.addDefeat(game.getCurrentAge());
				left.addVictory(game.getCurrentAge(), thisAgeVictory);
			}
			else if (myArmy > leftArmy) {
				p.addVictory(game.getCurrentAge(), thisAgeVictory);
				left.addDefeat(game.getCurrentAge());
			}	
		});

		return null;
	}
	
}
