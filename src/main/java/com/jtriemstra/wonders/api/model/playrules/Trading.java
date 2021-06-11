package com.jtriemstra.wonders.api.model.playrules;

import java.util.List;
import java.util.stream.Collectors;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

public class Trading extends PlayRule {
	
	@Override
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor, int coinDiscount) {

		int coinsAvailableForTrade = p.getCoins() - c.getCoinCost();
				
		if (leftNeighbor != null && rightNeighbor != null) {
			List<ResourceSet> leftResources = filterNeighborResources(leftNeighbor.getResources(false), currentNeed);
			List<ResourceSet> rightResources = filterNeighborResources(rightNeighbor.getResources(false), currentNeed);
			TradingResourceEvaluator2 eval1 = new TradingResourceEvaluator2(unused, leftResources, rightResources, coinsAvailableForTrade, currentNeed, p.getTradingProviders());
			List<TradeCost> costs = eval1.findMinCost();

			if (costs.size() == 1) {
				TradeCost cost = costs.get(0);
				return new CardPlayable(c, Status.OK, cost.left + cost.right, cost.left, cost.right, Math.max(0, c.getCoinCost() - coinDiscount));
			}
			else if (costs.size() > 1) {
				return new CardPlayable(c, Status.OK, costs, Math.max(0, c.getCoinCost() - coinDiscount));
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
