package com.jtriemstra.wonders.api.model.resource;

import java.util.Iterator;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ResourceWithTradeCost {

	private int cost;
	private ResourceSet resource;
	private String sourceName;
	
	public ResourceWithTradeCost(ResourceSet r, int coinCost, String sourceName) {
		this.cost = coinCost;
		this.resource = r;
		this.sourceName = sourceName;
	}
	
	public Iterator<ResourceType> iterator() {
		return resource.iterator();
	}
	
}
