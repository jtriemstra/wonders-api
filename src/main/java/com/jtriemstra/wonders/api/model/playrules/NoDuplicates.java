package com.jtriemstra.wonders.api.model.playrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class NoDuplicates extends PlayRule {
	
	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor) {
		if (p.hasPlayedCard(c)) {
			return new CardPlayable(c, Status.ERR_DUPLICATE, 0, 0, 0);
		}
		else {
			return getNextRule().evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor);
		}
	}

	@Override
	public double getOrder() {
		return 1.0;
	}

}
