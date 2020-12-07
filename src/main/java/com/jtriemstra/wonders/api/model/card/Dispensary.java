package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Dispensary extends ScienceCard {

	public Dispensary(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Dispensary";
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Lodge", "Arena"};
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.GLASS, ResourceType.ORE, ResourceType.ORE};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.COMPASS);
	}
}
