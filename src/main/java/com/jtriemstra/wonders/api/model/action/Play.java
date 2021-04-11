package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.PlayResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.BankPayment;
import com.jtriemstra.wonders.api.model.resource.TradingPayment;

public class Play implements BaseAction {
	private String[] validCards;
	private List<CardPlayable> cardCosts;
	
	public Play(List<CardPlayable> cardCosts) {
		this.cardCosts = cardCosts;
		
		List<String> validCardNames = new ArrayList<>();
		for (CardPlayable cp : cardCosts) {
			if (cp.getStatus() == Status.OK) {
				validCardNames.add(cp.getCard().getName());
			}
		}
		
		validCards = validCardNames.toArray(new String[validCardNames.size()]);
	}
	
	@Override
	public String getName() {
		return "play";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		ActionRequest actionRequest = (ActionRequest) request;
		
		validateCard(actionRequest.getCardName());
		
		Card c;
		c = player.getCardFromHand(actionRequest.getCardName());
		
		CardPlayable playedCard = null;
		for (CardPlayable cp : cardCosts) {
			if (cp.getCard().getName().equals(actionRequest.getCardName())) {
				playedCard = cp;
				break;
			}
		}
		
		//TODO: can I integrate the PlayRules here to accomodate Maecenas? And maybe get rid of the events with something more strongly typed
		if (playedCard.getBankCost() > 0) {
			player.schedulePayment(new BankPayment(c.getCoinCost(), player));	
		}		
		
		player.scheduleCardToPlay(c);
		
		player.eventNotify("play." + c.getType());
		
		if (player.canPlayByChain(actionRequest.getCardName())) {
			player.eventNotify("play.free");
		}
		
		player.popAction();
				
		if (playedCard.getLeftCost() > 0) {
			player.schedulePayment(new TradingPayment(playedCard.getLeftCost(), player, game.getLeftOf(player)));
			player.eventNotify("trade.neighbor");
		}
		if (playedCard.getRightCost() > 0) {
			player.schedulePayment(new TradingPayment(playedCard.getRightCost(), player, game.getRightOf(player)));
			player.eventNotify("trade.neighbor");
		}
		
		return new PlayResponse();
	}

	public List<CardPlayable> getCardCosts() {
		return cardCosts;
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
