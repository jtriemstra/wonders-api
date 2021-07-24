package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResolveCommerceAction implements NonPlayerAction, PostTurnAction {
	private Player singlePlayerToExecute;
	private double order;

	public ResolveCommerceAction() {
		order = 0.1;
	}
	
	public ResolveCommerceAction(Player p) {
		singlePlayerToExecute = p;
		order = 0.1;
	}
	
	@Override
	public double getOrder() {
		return order;
	}

	@Override
	public String getName() {
		return "resolveCommerce";
	}

	@Override
	public ActionResponse execute(Game game) {
		log.info("executing ResolveCommerceAction");
		
		if (singlePlayerToExecute != null) {
			game.removePostTurnAction(singlePlayerToExecute, getClass());
			singlePlayerToExecute.resolveCommerce();
			return null;
		}

		game.doForEachPlayer(p -> p.resolveCommerce());
		
		return null;
	}
}
