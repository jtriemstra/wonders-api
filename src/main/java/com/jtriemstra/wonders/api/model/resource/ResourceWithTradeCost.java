package com.jtriemstra.wonders.api.model.resource;

import java.util.Iterator;

import lombok.Data;

@Data
public class ResourceWithTradeCost {

	private int cost;
	private ResourceSet resource;
	private boolean isLeft;
	
	public ResourceWithTradeCost(ResourceSet r, int coinCost, boolean isLeft) {
		this.cost = coinCost;
		this.resource = r;
		//NOTE: this could actually get called for something that is on current player's board (so neither left nor right), but the cost will be zero, so should be irrelevant
		this.isLeft = isLeft;
	}
	
	public Iterator<ResourceType> iterator() {
		return resource.iterator();
	}
	
}
