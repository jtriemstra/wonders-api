package com.jtriemstra.wonders.api.model.card.provider;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.RequestDirection;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class TradingProviderList {
	
	private List<TradingProvider> trades = new ArrayList<>();
	
	public void add(TradingProvider t) {
		trades.add(t);
	}
	
	public int cost(ResourceSet r, RequestDirection direction) {
		int minCost = 2;
		for (TradingProvider t : trades) {
			int cost = t.cost(r, direction);
			if (cost < minCost) {
				minCost = cost;
			}
		}
		
		return minCost;
	}
}
