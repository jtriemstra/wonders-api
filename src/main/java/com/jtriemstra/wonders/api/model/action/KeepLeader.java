package com.jtriemstra.wonders.api.model.action;

import java.util.List;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.CardNameRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.DiscardResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;

public class KeepLeader implements BaseAction {
	@Override
	public String getName() {
		return "keepLeader";
	}

	public KeepLeader(List<CardPlayable> cards) {
		//TODO: store this for validation
	}
	
	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		CardNameRequest keepRequest = (CardNameRequest) request;
		Card c = player.removeCardFromHand(keepRequest.getCardName());
		player.keepLeader(c);

		player.popAction();
		return new DiscardResponse();
	}
}
