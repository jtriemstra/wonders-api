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
	
	public int getCost() {
		return buildable.getCost();
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		ActionRequest actionRequest = (ActionRequest) request;
		Card c = player.removeCardFromHand(actionRequest.getCardName());
		
		if (buildable.getLeftCost() > 0) {
			player.schedulePayment(new TradingPayment(buildable.getLeftCost(), player, game.getLeftOf(player)));
		}
		if (buildable.getRightCost() > 0) {
			player.schedulePayment(new TradingPayment(buildable.getRightCost(), player, game.getRightOf(player)));
		}
		
		player.popAction();
		
		WonderStage builtStage = player.build(game);
		player.spendCoins(builtStage.getCoinCost());
		
		BuildResponse r = new BuildResponse();
		r.setBuildState(player.getBuildState());
		return r;
	}
}
