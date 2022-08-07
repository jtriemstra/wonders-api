package com.jtriemstra.wonders.api.model.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult.PaymentFunction;

import lombok.Getter;
import lombok.Setter;

public class TradingResourceEvaluator2 {
	private List<HashMap<ResourceType, Integer>> results;
	private int maxTradeCost;
	private ResourceCost remainingResourceCost;
	private TradingProviderList trades;
	private List<ResourceWithTradeCost> allAvailableResources;
	private List<TradeCost> possibleFinalCosts = new ArrayList<>();
	
	public TradingResourceEvaluator2(List<EvalInfo> resourceSources, int maxTradeCost, ResourceCost remainingResourceCost) {
		this.maxTradeCost = maxTradeCost;
		this.remainingResourceCost = remainingResourceCost;
		
		this.allAvailableResources = new ArrayList<>();
		resourceSources.forEach( e -> this.allAvailableResources.addAll(e.getResources()));
	}
	
	public List<TradeCost> findMinCost() {
		Map<String, Integer> initialCostsBySource = new HashMap<>();
		this.allAvailableResources.forEach( r -> initialCostsBySource.putIfAbsent(r.getSourceName(), 0));
		recurse(this.allAvailableResources, new ArrayList<>(), initialCostsBySource, 0);
		return possibleFinalCosts;
	}
	
	private void recurse(List<ResourceWithTradeCost> input, List<ResourceType> usedResources, Map<String, Integer> knownCostsBySource, int inputStackPosition) {
		
		
		if (knownCostsBySource.values().stream().mapToInt(i -> i).sum() > this.maxTradeCost) {
			return;
		}
		if (remainingResourceCost.isSatisfiedBy(usedResources)) {
			TradeCost newCost = new TradeCost(knownCostsBySource);
			boolean addNewCost = true;
			for (int i=0; i<possibleFinalCosts.size(); ) {
				if (newCost.shouldKeep(possibleFinalCosts.get(i))) {
					addNewCost = false;
					break;
				}
				else if (newCost.shouldReplace(possibleFinalCosts.get(i))) {
					possibleFinalCosts.remove(i);					
				}
				else {
					i++;
				}
			}
			
			if (addNewCost) {
				possibleFinalCosts.add(newCost);
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
					usedResources.add(0, t);
					knownCostsBySource.merge(r.getSourceName(), r.getCost(), Integer::sum);					
					recurse(input, usedResources, knownCostsBySource, j+1);
					knownCostsBySource.merge(r.getSourceName(), -1 * r.getCost(), Integer::sum);
					usedResources.remove(0);
				}
			}			
		}
	}
	
	public class TradeCost {
		@Getter
		private Map<String, Integer> knownCostsBySource = new HashMap<>();
		@Setter
		private PaymentFunction payFunction;
		
		public TradeCost(Map<String, Integer> knownCostsBySource) { 
			knownCostsBySource.keySet().forEach( source -> this.knownCostsBySource.put(source, knownCostsBySource.get(source)));
		}
		public boolean shouldReplace(TradeCost existing) {
			for (String s : knownCostsBySource.keySet()) {
				if (this.knownCostsBySource.get(s) > existing.knownCostsBySource.get(s)) {
					return false;
				}
			}
			return true;
		}
		public boolean shouldKeep(TradeCost existing) {
			for (String s : knownCostsBySource.keySet()) {
				if (this.knownCostsBySource.get(s) < existing.knownCostsBySource.get(s)) {
					return false;
				}
			}
			return true;
		}
		public int get(String s) {
			if (knownCostsBySource.containsKey(s)) {
				return knownCostsBySource.get(s);
			}
			return 0;
		}
		
		@JsonIgnore
		public PaymentFunction getPayFunction() {
			return payFunction;
		}
	}
	
}
