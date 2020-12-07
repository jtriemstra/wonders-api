package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Workshop extends ScienceCard {

	public Workshop(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Workshop";
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Laboratory", "Archery Range"};
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.GLASS};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.GEAR);
	}
}
