package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.BuildResponse;
import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.resource.TradingPayment;

public class Build implements BaseAction {
	private Buildable buildable;
	
	public Build(Buildable buildable) {
		this.buildable = buildable;
	}

	@Override
	public String getName() {
		return "build";
	}
	
	public Buildable getBuildable() {
		return buildable;
	}

	//TODO: allow multiple payment choices like Play has
	//TODO: this really shouldn't happen until all players are done, in post-turn actions
	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		ActionRequest actionRequest = (ActionRequest) request;
		Card c = player.removeCardFromHand(actionRequest.getCardName());
		
		int leftCost = 0, rightCost = 0;
		if (buildable.getCostOptions() == null || buildable.getCostOptions().size() == 0) {
			leftCost = buildable.getLeftCost();
			rightCost = buildable.getRightCost();
		}
		else {
			leftCost = buildable.getCostOptions().get(actionRequest.getTradingInfo().getPlayableIndex()).left;
			rightCost = buildable.getCostOptions().get(actionRequest.getTradingInfo().getPlayableIndex()).right;
		}
		
		if (leftCost > 0) {
			player.schedulePayment(new TradingPayment(leftCost, player, game.getLeftOf(player)));
			player.eventNotify("trade.neighbor");
		}
		if (rightCost > 0) {
			player.schedulePayment(new TradingPayment(rightCost, player, game.getRightOf(player)));
			player.eventNotify("trade.neighbor");
		}
		
		//TODO: account for bank coin costs (Petra?)
		
		player.popAction();
		
		WonderStage builtStage = player.build(game);
		player.spendCoins(builtStage.getCoinCost());
		
		BuildResponse r = new BuildResponse();
		r.setBuildState(player.getBuildState());
		return r;
	}
}
