package com.jtriemstra.wonders.api.model.action;

import java.util.HashSet;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.PlayResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;

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
		
		Card c;
		c = player.getCardFromHand(actionRequest.getCardName());
		
		player.scheduleCardToPlay(c);
		
		player.eventNotify("play." + c.getType());
		
		usedInAges.add(game.getCurrentAge());
		
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

}
