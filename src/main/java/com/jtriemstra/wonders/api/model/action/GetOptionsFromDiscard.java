package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;

public class GetOptionsFromDiscard extends GetOptions implements PostTurnAction {
	
	private double order = 1.5;
	
	public GetOptionsFromDiscard() {
		
	}
	
	public GetOptionsFromDiscard(double order) {
		this.order = order;
	}
	
	@Override
	public boolean isSingleUse() {
		return true;
	}

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		player.popAction();

		Card[] discards = game.getDiscardCards();
		List<CardPlayable> playableDiscards = new ArrayList<>();
		List<String> validCardNames = new ArrayList<>();
		
		for (Card c : discards) {
			if (!player.hasPlayedCard(c)) {
				playableDiscards.add(new CardPlayable(c, Status.OK, 0, 0, 0));
				validCardNames.add(c.getName());
			}
		}
		
		if (validCardNames.size() > 0) {
			PlayHalikarnassos play = new PlayHalikarnassos(validCardNames.toArray(new String[validCardNames.size()]));
			
			player.addNextAction(play);
			
			OptionsResponse r = new OptionsResponse();
			r.setCards(playableDiscards);
			
			return r;	
		}
		else {
			OptionsResponse r = new OptionsResponse();
			r.setMessage("Either there are no cards in the discard pile, or they all duplicate cards you already have");
			
			return r;
		}
		
	}

	@Override
	public double getOrder() {
		return order;
	}

}
