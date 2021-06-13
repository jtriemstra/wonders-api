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
	
	private List<CardPlayable> validCards;
	
	@Override
	public String getName() {
		return "keepLeader";
	}

	public KeepLeader(List<CardPlayable> cards) {
		validCards = cards;
	}
	
	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		CardNameRequest keepRequest = (CardNameRequest) request;
		String cardName = keepRequest.getCardName();
		
		if (!validCards.stream().anyMatch(cp -> cp.getCard().getName().equals(cardName))) {
			throw new RuntimeException("this card is not available to play");
		}
		
		Card c = player.removeCardFromHand(cardName);
		player.keepLeader(c);

		player.popAction();
		return new DiscardResponse();
	}
}
