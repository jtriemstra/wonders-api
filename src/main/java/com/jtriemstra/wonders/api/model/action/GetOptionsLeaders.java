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
import com.jtriemstra.wonders.api.model.leaders.PlayerLeaders;

public class GetOptionsLeaders extends GetOptions {
	
	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		player.popAction();
		
		List<CardPlayable> playableCards = new ArrayList<>();
		
		for (Card c : player.getHandCards()) {
			
			//TODO: (low) "playable" is a misnomer here - maybe CardActionable
			playableCards.add(new CardPlayable(c, Status.OK, 0, 0, 0));
		}
		
		for (Card c : ((PlayerLeaders) player).getLeaderCards()) {
			playableCards.add(new CardPlayable(c, Status.ERR_DUPLICATE, 0, 0, 0));
		}
		
		KeepLeader keep = new KeepLeader(playableCards);
		
		player.addNextAction(keep);
		
		OptionsResponse r = new OptionsResponse();
		r.setCards(playableCards);
		
		return r;	
		
	}

}
