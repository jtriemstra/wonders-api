package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;

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
		
		if (!game.getFlow().isAgeStarted() || !game.getFlow().isFinalTurn()) {
			return null;
		}

		int thisAgeVictory;
		if (game.getFlow().getCurrentAge() == 1) thisAgeVictory = 1;
		else if (game.getFlow().getCurrentAge() == 2) thisAgeVictory = 3;
		else if (game.getFlow().getCurrentAge() == 3) thisAgeVictory = 5;
		else throw new RuntimeException("invalid age found");
		
		game.doForEachPlayer(p -> {
			IPlayer left = game.getLeftOf(p);
			
			int myArmy = p.getArmyFacade().getArmies();
			int leftArmy = left.getArmyFacade().getArmies();
			
			if (myArmy < leftArmy) {
				p.getArmyFacade().addDefeat(game.getFlow().getCurrentAge());
				left.getArmyFacade().addVictory(game.getFlow().getCurrentAge(), thisAgeVictory);
				left.eventNotify("conflict.victory");
			}
			else if (myArmy > leftArmy) {
				p.getArmyFacade().addVictory(game.getFlow().getCurrentAge(), thisAgeVictory);
				p.eventNotify("conflict.victory");
				left.getArmyFacade().addDefeat(game.getFlow().getCurrentAge());
			}	
		});

		return null;
	}
	
}
