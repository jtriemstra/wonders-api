package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayCardsAction implements NonPlayerAction, PostTurnAction {
	private Player singlePlayerToExecute;
	private double order;

	public PlayCardsAction() {
		order = 0.0;
	}
	
	public PlayCardsAction(Player p, double order) {
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
			game.removePostTurnAction(singlePlayerToExecute, getClass());
			singlePlayerToExecute.playScheduledCard(game);
			singlePlayerToExecute.buildScheduledCard(game);
			return null;
		}
		
		game.doForEachPlayer(p -> { 
			p.playScheduledCard(game);
			p.buildScheduledCard(game);
		});
		
		return null;
	}
}
