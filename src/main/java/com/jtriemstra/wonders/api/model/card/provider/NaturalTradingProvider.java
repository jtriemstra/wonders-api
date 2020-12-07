package com.jtriemstra.wonders.api.model.card.provider;

import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.RequestDirection;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class NaturalTradingProvider extends TradingProvider {
	
	public NaturalTradingProvider(CardDirection direction) {
		super(direction);
	}
	
	@Override
	public int cost(ResourceSet r, RequestDirection direction) {
		
		if (this.direction == CardDirection.BOTH || (this.direction == CardDirection.LEFT && direction == RequestDirection.LEFT) || (this.direction == CardDirection.RIGHT && direction == RequestDirection.RIGHT)) {
			if (r.matches(ResourceType.WOOD) || r.matches(ResourceType.STONE) || r.matches(ResourceType.ORE) || r.matches(ResourceType.BRICK)) {
				return 1;
			}
		}
		
		return 2;
	}
}
