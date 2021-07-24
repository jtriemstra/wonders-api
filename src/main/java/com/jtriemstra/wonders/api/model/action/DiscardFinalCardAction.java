package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.DiscardPile;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class DiscardFinalCardAction implements NonPlayerAction, PostTurnAction {
	
	private DiscardPile discard;
	
	@Override
	public double getOrder() {
		return 1;
	}

	@Override
	public String getName() {
		return "discardFinal";
	}

	@Override
	public ActionResponse execute(Game game) {
		if (!game.isAgeStarted() || !game.isFinalTurn()) {
			return null;
		}
		
		game.doForEachPlayer(p -> p.discardHand(discard));
		
		return null;
	}
}
