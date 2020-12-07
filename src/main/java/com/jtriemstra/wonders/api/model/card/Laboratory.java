package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Laboratory extends ScienceCard {

	public Laboratory(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Laboratory";
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Observatory", "Siege Workshop"};
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.PAPER, ResourceType.BRICK, ResourceType.BRICK};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.GEAR);
	}
}
