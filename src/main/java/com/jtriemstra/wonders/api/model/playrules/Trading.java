package com.jtriemstra.wonders.api.model.playrules;

import java.util.List;
import java.util.stream.Collectors;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator;

public class Trading extends PlayRule {
	
	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor, int coinDiscount) {

		int coinsAvailableForTrade = p.getCoins() - c.getCoinCost();
				
		if (leftNeighbor != null && rightNeighbor != null) {
			List<ResourceSet> leftResources = filterNeighborResources(leftNeighbor.getResources(false), currentNeed);
			List<ResourceSet> rightResources = filterNeighborResources(rightNeighbor.getResources(false), currentNeed);
			TradingResourceEvaluator eval1 = new TradingResourceEvaluator(unused, leftResources, rightResources, coinsAvailableForTrade, currentNeed, p.getTradingProviders());
			int minCost = eval1.findMinCost();
			//TODO: (low) better flag for too expensive
			if (minCost < 100) {
				return new CardPlayable(c, Status.OK, minCost, eval1.getLeftCost(), eval1.getRightCost(), c.getCoinCost() - coinDiscount);
			}
		}
		
		return getNextRule().evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor, coinDiscount);
	}

	@Override
	public double getOrder() {
		return 7.0;
	}

	private List<ResourceSet> filterNeighborResources(List<ResourceSet> input, ResourceCost resourcesNeeded){
		return input.stream().filter(r -> resourcesNeeded.isNeeded(r)).collect(Collectors.toList());
	}
}
