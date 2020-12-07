package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Observatory extends ScienceCard {

	public Observatory(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Observatory";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.TEXTILE, ResourceType.ORE, ResourceType.ORE, ResourceType.GLASS};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.GEAR);
	}
}
