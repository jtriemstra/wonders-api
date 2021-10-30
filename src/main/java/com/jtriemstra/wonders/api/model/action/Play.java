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
		
		CardPlayable playedCard = null;
		for (CardPlayable cp : cardCosts) {
			if (cp.getCard().getName().equals(actionRequest.getCardName())) {
				playedCard = cp;
				break;
			}
		}
		
		//TODO: can I integrate the PlayRules here to accomodate Maecenas? And maybe get rid of the events with something more strongly typed
		//TODO: Bilkis is going to require multiple options for bank cost, like left/right cost have
		if (playedCard.getBankCost() > 0) {
			player.schedulePayment(new BankPayment(playedCard.getBankCost(), player));	
		}		
		
		player.scheduleTurnAction(() -> doPlay(player, game, actionRequest.getCardName()));
				
		if (player.canPlayByChain(actionRequest.getCardName())) {
			player.eventNotify("play.free");
		}
		
		player.popAction();
		
		int leftCost = 0, rightCost = 0;
		if (playedCard.getCostOptions() == null || playedCard.getCostOptions().size() == 0) {
			leftCost = playedCard.getLeftCost();
			rightCost = playedCard.getRightCost();
		}
		else {
			leftCost = playedCard.getCostOptions().get(actionRequest.getTradingInfo().getPlayableIndex()).left;
			rightCost = playedCard.getCostOptions().get(actionRequest.getTradingInfo().getPlayableIndex()).right;
		}
		
		if (leftCost > 0) {
			player.schedulePayment(new TradingPayment(leftCost, player, game.getLeftOf(player)));
			player.eventNotify("trade.neighbor");
		}
		if (rightCost > 0) {
			player.schedulePayment(new TradingPayment(rightCost, player, game.getRightOf(player)));
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
	
	public void doPlay(Player p, Game g, String cardName) {
		Card c = p.removeCardFromHand(cardName);
		p.eventNotify("play." + c.getType());
		c.play(p, g);
		p.putCardOnBoard(c);	
		
		//notifications.addNotification(name + " played " + this.cardToPlay.getName());		
	}
}
