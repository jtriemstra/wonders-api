package com.jtriemstra.wonders.api.model.card.provider;

import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class TechTradingProvider extends TradingProvider {
	public TechTradingProvider(CardDirection direction) {
		super(direction);
	}
	
	@Override
	public int cost(ResourceSet r, RequestDirection direction) {
		
		if (this.direction == CardDirection.BOTH || (this.direction == CardDirection.LEFT && direction == RequestDirection.LEFT) || (this.direction == CardDirection.RIGHT && direction == RequestDirection.RIGHT)) {
			if (r.matches(ResourceType.GLASS) || r.matches(ResourceType.PAPER) || r.matches(ResourceType.TEXTILE)) {
				return 1;
			}	
		}
		
		return 2;
	}
}
