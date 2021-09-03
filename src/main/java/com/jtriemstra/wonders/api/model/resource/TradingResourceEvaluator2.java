package com.jtriemstra.wonders.api.model.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.RequestDirection;

public class TradingResourceEvaluator2 {
	private List<HashMap<ResourceType, Integer>> results;
	private int maxTradeCost;
	private ResourceCost remainingResourceCost;
	private TradingProviderList trades;
	private List<ResourceWithTradeCost> input;
	private List<TradeCost> validCosts = new ArrayList<>();
	
	
	public TradingResourceEvaluator2(List<ResourceSet> board, List<ResourceSet> left, List<ResourceSet> right, int maxTradeCost, ResourceCost remainingResourceCost, TradingProviderList trades) {

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
	
	public List<TradeCost> findMinCost() {
		recurse(this.input, new ArrayList<>(), 0, 0, 0, 0);
		return validCosts;
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
	
	private void recurse(List<ResourceWithTradeCost> input, List<ResourceType> result, int cost, int leftCost, int rightCost, int inputStackPosition) {
		
		if (cost > this.maxTradeCost) {
			return;
		}
		if (remainingResourceCost.isSatisfiedBy(result)) {
			TradeCost newCost = new TradeCost(leftCost, rightCost);
			boolean addNewCost = true;
			for (int i=0; i<validCosts.size(); ) {
				if (newCost.shouldKeep(validCosts.get(i))) {
					addNewCost = false;
					break;
				}
				else if (newCost.shouldReplace(validCosts.get(i))) {
					validCosts.remove(i);					
					//break;
				}
				else {
					i++;
				}
			}
			
			if (addNewCost) {
				validCosts.add(newCost);
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
	
	public class TradeCost {
		public int left, right;
		public TradeCost(int left, int right) {this.left = left; this.right = right;}
		public boolean shouldReplace(TradeCost existing) { return (this.left <= existing.left && this.right <= existing.right); }
		public boolean shouldKeep(TradeCost existing) { return (this.left >= existing.left && this.right >= existing.right); }
	}
	
}
