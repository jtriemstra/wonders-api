package com.jtriemstra.wonders.api.model.playrules.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.playrules.PlayRule;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DiscountByType extends PlayRule {

	private Class cardClass;
	
	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor, int coinDiscount) {
		if (cardClass.isInstance(c)) {
			unused.add(new ResourceSet(ResourceType.BRICK, ResourceType.GLASS, ResourceType.ORE, ResourceType.PAPER, ResourceType.STONE, ResourceType.TEXTILE, ResourceType.WOOD));
		}
		
		return getNextRule().evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor, coinDiscount);
	}

	@Override
	public double getOrder() {
		return 4.5;
	}

}
