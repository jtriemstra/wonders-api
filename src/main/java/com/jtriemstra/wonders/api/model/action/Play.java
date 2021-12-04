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
import com.jtriemstra.wonders.api.notifications.NotificationService;

import lombok.Setter;

public class Play implements BaseAction {
	private String[] validCards;
	private List<CardPlayable> cardCosts;
	private CardRemoveStrategy removal;
	
	public Play(List<CardPlayable> cardCosts, CardRemoveStrategy removal) {
		this.cardCosts = cardCosts;
		this.removal = removal;
		
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
		
		int selectedPlayableOptionIndex = actionRequest.getTradingInfo() != null ? actionRequest.getTradingInfo().getPlayableIndex() : 0;
		
		player.scheduleTurnAction(notifications -> doPlay(player, game, actionRequest.getCardName(), notifications, selectedPlayableOptionIndex));
		
		player.popAction();
				
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
	
	public void doPlay(Player p, Game g, String cardName, NotificationService notifications, int playableIndex) {
		Card c = removal.removeFromSource(cardName);
		p.eventNotify("play." + c.getType());
		c.play(p, g);
		p.putCardOnBoard(c);	
		
		CardPlayable playedCard = null;
		for (CardPlayable cp : cardCosts) {
			if (cp.getCard().getName().equals(cardName)) {
				playedCard = cp;
				break;
			}
		}
		
		//TODO: can I integrate the PlayRules here to accomodate Maecenas? And maybe get rid of the events with something more strongly typed
		//TODO: Bilkis is going to require multiple options for bank cost, like left/right cost have
		if (playedCard.getBankCost() > 0) {
			p.gainCoins(-1 * playedCard.getBankCost());	
		}	
		
		if (p.canPlayByChain(cardName)) {
			p.eventNotify("play.free");
		}

		int leftCost = 0, rightCost = 0;
		if (playedCard.getCostOptions() == null || playedCard.getCostOptions().size() == 0) {
			leftCost = playedCard.getLeftCost();
			rightCost = playedCard.getRightCost();
		}
		else {
			leftCost = playedCard.getCostOptions().get(playableIndex).left;
			rightCost = playedCard.getCostOptions().get(playableIndex).right;
		}
		
		if (leftCost > 0) {
			p.gainCoins(-1 * leftCost);
			g.getLeftOf(p).gainCoins(leftCost);
			p.eventNotify("trade.neighbor");
		}
		if (rightCost > 0) {
			p.gainCoins(-1 * rightCost);
			g.getRightOf(p).gainCoins(rightCost);
			p.eventNotify("trade.neighbor");
		}
		
		notifications.addNotification(p.getName() + " played " + cardName);		
	}
}
