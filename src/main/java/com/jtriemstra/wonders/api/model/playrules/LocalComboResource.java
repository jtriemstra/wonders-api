package com.jtriemstra.wonders.api.model.playrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.LocalResourceEvaluator;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class LocalComboResource extends PlayRule {

	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor, int coinDiscount) {
		LocalResourceEvaluator eval = new LocalResourceEvaluator(unused);
		if (eval.test(currentNeed)) {
			return new CardPlayable(c, Status.OK, 0, 0, 0, c.getCoinCost() - coinDiscount);
		}
		
		return getNextRule().evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor, coinDiscount);
	}

	@Override
	public double getOrder() {
		return 6.0;
	}

}
