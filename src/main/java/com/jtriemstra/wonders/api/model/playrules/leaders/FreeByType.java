package com.jtriemstra.wonders.api.model.playrules.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.leaders.LeaderCard;
import com.jtriemstra.wonders.api.model.playrules.PlayRule;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FreeByType extends PlayRule {

	private Class cardClass;
	
	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor) {
		if (cardClass.isInstance(c)) {
			return new CardPlayable(c, Status.OK, 0, 0, 0, 0);
		}
		else {
			return getNextRule().evaluate(c, p, currentNeed, unused, null, null);
		}
	}

	@Override
	public double getOrder() {
		return 1.5;
	}

}
