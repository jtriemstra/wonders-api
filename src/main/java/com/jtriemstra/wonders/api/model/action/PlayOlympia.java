package com.jtriemstra.wonders.api.model.action;

import java.util.HashSet;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.PlayResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.notifications.NotificationService;

public class PlayOlympia implements BaseAction {
	private String[] validCards;
	private HashSet<Integer> usedInAges;
	
	public PlayOlympia(String[] validCards, HashSet<Integer> usedInAges) {
		this.validCards = validCards;
		this.usedInAges = usedInAges;
	}
	
	@Override
	public String getName() {
		return "playFree";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		ActionRequest actionRequest = (ActionRequest) request;
		
		validateCard(actionRequest.getCardName());
		
		player.scheduleTurnAction(notifications -> doPlay(player, game, actionRequest.getCardName(), notifications));
		
		usedInAges.add(game.getFlow().getCurrentAge());
		
		player.popAction();
		
		return new PlayResponse();
	}
	
	private void validateCard(String cardName) {
		boolean cardIsValid = false;
		for (String s : this.validCards) {
			if (s.equals(cardName)) {
				cardIsValid = true;
			}
		}
		
		if (!cardIsValid) {
			throw new RuntimeException("card not found in hand");
		}
	}

	public void doPlay(Player p, Game g, String cardName, NotificationService notifications) {
		Card c = p.removeCardFromHand(cardName);
		p.eventNotify("play." + c.getType());
		c.play(p, g);
		p.putCardOnBoard(c);	
		
		notifications.addNotification(p.getName() + " played " + cardName);		
	}
}
