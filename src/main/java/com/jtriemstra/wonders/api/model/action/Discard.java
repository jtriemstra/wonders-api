package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.DiscardResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;

public class Discard implements BaseAction {
	@Override
	public String getName() {
		return "discard";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		DiscardRequest discardRequest = (DiscardRequest) request;
		Card c;
		c = player.getCardFromHand(discardRequest.getCardName());
		
		player.scheduleCardToDiscard(c);
		//TODO: this is a little odd - putting it here for now so Discard and DiscardLeader can share the discardScheduledCard() method in  Player 
		game.discard(c);
		
		player.addCoinProvider(new SimpleCoinProvider(3));
		
		player.popAction();
		return new DiscardResponse();
	}
	
	
}
