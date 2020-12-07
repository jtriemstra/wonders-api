package com.jtriemstra.wonders.api.model.card.provider;

import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public abstract class TradingProvider {
	
	protected CardDirection direction;
	
	public TradingProvider(CardDirection direction) {
		this.direction = direction;
	}

	public abstract int cost(ResourceSet r, RequestDirection direction);
	
	public enum CardDirection {
		LEFT,
		RIGHT,
		BOTH
	}
	
	public enum RequestDirection {
		LEFT,
		RIGHT
	}
}
