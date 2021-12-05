package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.leaders.PlayerLeaders;

public class GetOptionsRecruitLeader extends GetOptions {

	@Override
	protected Card[] getCardsToEvaluate(IPlayer p) {
		return ((PlayerLeaders)p).getLeaderCards();
	}
	
	@Override
	protected BaseAction createDiscardAction(IPlayer player, Game game) {
		return new DiscardLeader(getRemoval(player));
	}
	
	@Override
	protected CardRemoveStrategy getRemoval(IPlayer player) {
		return cardName -> ((PlayerLeaders)player).removeCardFromLeaders(cardName);
	}
}
