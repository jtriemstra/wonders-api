package com.jtriemstra.wonders.api.model.action;

import java.util.List;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.PlayResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.resource.TradingPayment;

public class PlayHalikarnassos implements BaseAction {
	private String[] validCards;
	
	public PlayHalikarnassos(String[] validCards) {
		this.validCards = validCards;
	}
	
	@Override
	public String getName() {
		return "playFree";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		//TODO: does this work if board discard play leads to Solomon discard play? (kind of a stupid sequence, with no net benefit)
		
		ActionRequest actionRequest = (ActionRequest) request;
		
		validateCard(actionRequest.getCardName());
		
		Card c;
		//TODO: (low) is there any impact to doing this immediately? would like to unify this with Play to keep things in sync
		c = game.removeFromDiscard(actionRequest.getCardName());
		player.putCardOnBoard(c);
		player.eventNotify("play." + c.getType());
		
		c.play(player, game);
		player.popAction();
		game.removePostTurnAction(player, GetOptionsFromDiscard.class);
		
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
