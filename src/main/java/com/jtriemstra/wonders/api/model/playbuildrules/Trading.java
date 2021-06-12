package com.jtriemstra.wonders.api.model.playbuildrules;

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

public class Trading extends Rule {
	
	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		List<ResourceSet> unused = actionEvaluating.getUnusedResources();
		ResourceCost currentNeed = actionEvaluating.getResourceCost();
		Player leftNeighbor = actionEvaluating.getLeftNeighbor();
		Player rightNeighbor = actionEvaluating.getRightNeighbor();
		
		int coinsAvailableForTrade = actionEvaluating.getCoinsAvailableForTrade();
				
		if (leftNeighbor != null && rightNeighbor != null) {
			List<ResourceSet> leftResources = filterNeighborResources(leftNeighbor.getResources(false), currentNeed);
			List<ResourceSet> rightResources = filterNeighborResources(rightNeighbor.getResources(false), currentNeed);
			TradingResourceEvaluator2 eval1 = new TradingResourceEvaluator2(unused, leftResources, rightResources, coinsAvailableForTrade, currentNeed, actionEvaluating.getTradingProviders());
			List<TradeCost> costs = eval1.findMinCost();

			if (costs.size() == 1) {
				TradeCost cost = costs.get(0);
				return new PlayableBuildableResult(actionEvaluating, Status.OK, actionEvaluating.getCoinCost(), cost.left, cost.right);
			}
			else if (costs.size() > 1) {
				return new PlayableBuildableResult(actionEvaluating, Status.OK, costs, actionEvaluating.getCoinCost());
			}
		}
		
		return getNextRule().evaluate(actionEvaluating);
	}

	@Override
	public double getOrder() {
		return 7.0;
	}

	private List<ResourceSet> filterNeighborResources(List<ResourceSet> input, ResourceCost resourcesNeeded){
		return input.stream().filter(r -> resourcesNeeded.isNeeded(r)).collect(Collectors.toList());
	}
}
