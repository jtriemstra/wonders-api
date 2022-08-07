package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.RequestDirection;
import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.resource.EvalInfo;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceWithTradeCost;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

public class Trading extends Rule {
	
	private TradingProviderList trades;
	
	protected List<EvalInfo> buildInitialResources() {
		return new ArrayList<>();
	}
	
	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		List<ResourceSet> unused = actionEvaluating.getUnusedResources();
		ResourceCost currentNeed = actionEvaluating.getResourceCost();
		IPlayer leftNeighbor = actionEvaluating.getLeftNeighbor();
		IPlayer rightNeighbor = actionEvaluating.getRightNeighbor();
		this.trades = actionEvaluating.getTradingProviders();
		
		int coinsAvailableForTrade = actionEvaluating.getCoinsAvailableForTrade();
				
		if (leftNeighbor != null && rightNeighbor != null) {
			List<ResourceSet> leftResources = filterNeighborResources(leftNeighbor.getResources(false), currentNeed);
			List<ResourceSet> rightResources = filterNeighborResources(rightNeighbor.getResources(false), currentNeed);
			
			List<EvalInfo> allResources = buildInitialResources();
			allResources.add( new EvalInfo( unused.stream().map( rs -> {return new ResourceWithTradeCost(rs, 0, "Self");} ).collect(Collectors.toList()), "Self"));
			allResources.add(new EvalInfo(findCosts(leftResources, RequestDirection.LEFT), "Left"));
			allResources.add(new EvalInfo(findCosts(rightResources, RequestDirection.RIGHT), "Right"));
			
						
			TradingResourceEvaluator2 eval1 = new TradingResourceEvaluator2(allResources, coinsAvailableForTrade, currentNeed);
			List<TradeCost> costs = eval1.findMinCost();

			if (costs.size() >= 1) {
				for (TradeCost tc : costs) {
					tc.setPayFunction((p,g) -> {
						int leftCost = tc.getKnownCostsBySource().containsKey("Left") ? tc.getKnownCostsBySource().get("Left") : 0;
						int rightCost = tc.getKnownCostsBySource().containsKey("Right") ? tc.getKnownCostsBySource().get("Right") : 0;
						
						if (leftCost > 0) {
							p.gainCoins(-1 * leftCost);
							leftNeighbor.gainCoins(leftCost);
							p.eventNotify("trade.neighbor");
						}
						if (rightCost > 0) {
							p.gainCoins(-1 * rightCost);
							rightNeighbor.gainCoins(rightCost);
							p.eventNotify("trade.neighbor");
						}
					});
				}
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
	

	private List<ResourceWithTradeCost> findCosts(List<ResourceSet> in, RequestDirection direction){
		List<ResourceWithTradeCost> out = new ArrayList<>();
		for (ResourceSet r : in) {
			out.add(new ResourceWithTradeCost(r, findCosts(r, direction), direction == RequestDirection.LEFT ? "Left" : "Right"));			
		}
		
		return out;
	}
	
	private int findCosts(ResourceSet r, RequestDirection direction) {
		
		if (trades != null) {
			return trades.cost(r, direction);
		}
		
		return 2;
	}
}

