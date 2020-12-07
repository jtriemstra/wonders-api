package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Apothecary extends ScienceCard {

	public Apothecary(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Apothecary";
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Stables", "Dispensary"};
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.TEXTILE};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.COMPASS);
	}
}
