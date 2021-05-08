package com.jtriemstra.wonders.api.model.playrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class LocalSingleResource extends PlayRule {

	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor, int coinDiscount) {
		for (int i=0; i<unused.size();) {
			if (unused.get(i).isSingle()) {
				ResourceType currentAvailableType = unused.get(i).getSingle();
				if (currentNeed.isNeeded(currentAvailableType)) {
					currentNeed.reduce(currentAvailableType);
				}
				unused.remove(i);
			}
			else if (!currentNeed.isNeeded(unused.get(i))) {
				unused.remove(i);
			}
			else {
				i++;
			}
			
			if (currentNeed.isComplete()) {
				return new CardPlayable(c, Status.OK, 0, 0, 0, Math.max(0, c.getCoinCost() - coinDiscount));
			}
		}
		
		return getNextRule().evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor, coinDiscount);
	}

	@Override
	public double getOrder() {
		return 5.0;
	}

}
