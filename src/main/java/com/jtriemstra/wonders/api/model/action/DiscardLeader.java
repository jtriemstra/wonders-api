package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.DiscardResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;

public class DiscardLeader implements BaseAction {
	//TODO: (low) could probably remove this class to reduce duplication w/ Discard by having a direct dependency to DiscardPile in the Discard action, and in the case of leaders, passing it a DummyDiscardPile
	@Override
	public String getName() {
		return "discard";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		DiscardRequest discardRequest = (DiscardRequest) request;
		Card c = player.removeCardFromHand(discardRequest.getCardName());

		player.addCoinProvider(new SimpleCoinProvider(3));
		
		player.popAction();
		return new DiscardResponse();
	}
}
