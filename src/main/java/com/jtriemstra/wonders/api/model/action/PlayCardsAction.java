package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayCardsAction implements NonPlayerAction, PostTurnAction {
	private IPlayer singlePlayerToExecute;
	private double order;

	public PlayCardsAction() {
		order = 0.0;
	}
	
	public PlayCardsAction(IPlayer p, double order) {
		singlePlayerToExecute = p;
		this.order = order;
	}
	
	@Override
	public double getOrder() {
		return order;
	}

	@Override
	public String getName() {
		return "playCards";
	}

	@Override
	public ActionResponse execute(Game game) {
		log.info("executing PlayCardsAction");
		if (singlePlayerToExecute != null) {			
			singlePlayerToExecute.doScheduledAction();
			return null;
		}
		
		game.doForEachPlayer(p -> { 
			p.doScheduledAction();
		});
		
		return null;
	}
}
