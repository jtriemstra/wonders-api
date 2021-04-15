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
public class CoinDiscountByType extends PlayRule {

	private Class cardClass;
	private int discount;
	
	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor, int coinDiscount) {
		if (cardClass.isInstance(c)) {
			return getNextRule().evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor, coinDiscount + discount);	
		}
		else {
			return getNextRule().evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor, coinDiscount);
		}
		
	}

	@Override
	public double getOrder() {
		return 2.9;
	}

}
