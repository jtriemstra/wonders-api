package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.DiscardResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.notifications.NotificationService;

public class Discard implements BaseAction {
		
	@Override
	public String getName() {
		return "discard";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		DiscardRequest discardRequest = (DiscardRequest) request;
		player.scheduleTurnAction(notifications -> doDiscard(player, game, discardRequest.getCardName(), notifications));
		
		player.addCoinProvider(new SimpleCoinProvider(3));
		
		player.popAction();
		return new DiscardResponse();
	}
	
	public void doDiscard(Player p, Game g, String cardName, NotificationService notifications) {
		Card c = p.removeCardFromHand(cardName);
		g.addToDiscard(c);
		notifications.addNotification(p.getName() + " discarded");
	}
}
