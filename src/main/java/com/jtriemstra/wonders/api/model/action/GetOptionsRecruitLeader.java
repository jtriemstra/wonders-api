package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;

public class GetOptionsRecruitLeader extends GetOptions {

	@Override
	protected Card[] getCardsToEvaluate(Player p) {
		return p.getLeaderCards();
	}
	
	@Override
	protected BaseAction createDiscardAction(Player player, Game game) {
		return new DiscardLeader(getRemoval(player));
	}
	
	@Override
	protected CardRemoveStrategy getRemoval(Player player) {
		return cardName -> player.removeCardFromLeaders(cardName);
	}
}
