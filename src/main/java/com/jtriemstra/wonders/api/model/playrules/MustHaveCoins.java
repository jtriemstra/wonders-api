package com.jtriemstra.wonders.api.model.playrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class MustHaveCoins extends PlayRule {

	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor, int coinDiscount) {
		if (c.getCoinCost() - coinDiscount > p.getCoins()) {
			return new CardPlayable(c, Status.ERR_COINS, 0, 0, 0);
		}
		else {
			return getNextRule().evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor, coinDiscount);
		}
	}

	@Override
	public double getOrder() {
		return 3.0;
	}

}
