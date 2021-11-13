package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.DiscardResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.notifications.NotificationService;

public class DiscardLeader implements BaseAction {

	private NotificationService notifications;
	
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
		p.removeCardFromHand(cardName);
		
		notifications.addNotification(p.getName() + " discarded");
	}
}
