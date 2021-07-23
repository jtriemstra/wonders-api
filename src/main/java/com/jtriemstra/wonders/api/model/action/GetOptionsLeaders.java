package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;

public class GetOptionsLeaders extends GetOptions {

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();
		
		List<CardPlayable> playableCards = new ArrayList<>();
		
		for (Card c : player.getHandCards()) {
			
			//TODO: (low) "playable" is a misnomer here
			playableCards.add(new CardPlayable(c, Status.OK, 0, 0, 0));
		}
		
		KeepLeader keep = new KeepLeader(playableCards);
		
		player.addNextAction(keep);
		
		OptionsResponse r = new OptionsResponse();
		r.setCards(playableCards);
		
		return r;	
		
	}

}
