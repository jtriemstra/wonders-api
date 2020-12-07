package com.jtriemstra.wonders.api.model.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.RequestDirection;

public class TradingResourceEvaluator {
	private List<HashMap<ResourceType, Integer>> results;
	private int maxTradeCost;
	private ResourceCost remainingResourceCost;
	private TradingProviderList trades;
	private List<ResourceWithTradeCost> input;
	private int minCost = 100;
	private int leftCost = 0;
	private int rightCost = 0;
	
	public TradingResourceEvaluator(List<ResourceSet> board, List<ResourceSet> left, List<ResourceSet> right, int maxTradeCost, ResourceCost remainingResourceCost, TradingProviderList trades) {

		this.maxTradeCost = maxTradeCost;
		this.remainingResourceCost = remainingResourceCost;
		this.trades = trades;
		
		this.input = new ArrayList<>();

		for (ResourceSet r : board) {
			this.input.add(new ResourceWithTradeCost(r, 0, false));
		}

		this.input.addAll(findCosts(left, RequestDirection.LEFT));
		this.input.addAll(findCosts(right, RequestDirection.RIGHT));
		
		results = new ArrayList<>();
		
	}
	
	//TODO: allow for different ways to pay different players
	public int findMinCost() {
		recurse(this.input, new ArrayList<>(), 0, 0, 0, 0);
		return minCost;
	}
	
	public int getLeftCost() {
		return leftCost;
	}
	
	public int getRightCost() {
		return rightCost;
	}
	
	private List<ResourceWithTradeCost> findCosts(List<ResourceSet> in, RequestDirection direction){
		List<ResourceWithTradeCost> out = new ArrayList<>();
		for (ResourceSet r : in) {
			out.add(new ResourceWithTradeCost(r, findCosts(r, direction), direction == RequestDirection.LEFT));			
		}
		
		return out;
	}
	
	private int findCosts(ResourceSet r, RequestDirection direction) {
		
		if (trades != null) {
			return trades.cost(r, direction);
		}
		
		return 2;
	}
	
	//TODO: (low) find a less-clunky top-down implementation of this, maybe with a proper stack?
	//TODO: (low) try a bottom-up implementation of this and compare
	//TODO: (low) try adding memoization and compare
	private void recurse(List<ResourceWithTradeCost> input, List<ResourceType> result, int cost, int leftCost, int rightCost, int inputStackPosition) {
		
		if (cost > this.maxTradeCost) {
			return;
		}
		if (remainingResourceCost.isSatisfiedBy(result)) {
			if (cost < minCost) {
				minCost = cost;
				this.leftCost = leftCost;
				this.rightCost = rightCost;			
			}
			return;
		}
		if (input.size() == 0) {
			return;
		}
		
		for (int j=inputStackPosition; j<input.size(); j++) {
			ResourceWithTradeCost r = input.get(j);
			Iterator<ResourceType> i = r.iterator();
			while(i.hasNext()) {
				ResourceType t = i.next(); 
				if (remainingResourceCost.isNeeded(t)) {
					result.add(0, t);
					recurse(input, result, cost + r.getCost(), 
							r.isLeft() ? leftCost + r.getCost() : leftCost, 
							!r.isLeft() ? rightCost + r.getCost() : rightCost,
							j+1);
					result.remove(0);
				}
			}			
		}
	}
	
	static int printLimit = 0;
	private void printList(List<ResourceType> in) {
		if (printLimit++ > 100) {
			return;
		}
		
		for (ResourceType r : in) {
			System.out.print(" " + r.toString() + " ");
		}
		System.out.println("");
	}
	
	private void printList2(List<ResourceWithTradeCost> in) {
		for (ResourceWithTradeCost r : in) {
			System.out.print(" " + r.toString() + " ");
		}
		System.out.println("");
	}
}
