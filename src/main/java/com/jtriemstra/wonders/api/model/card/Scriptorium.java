package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Scriptorium extends ScienceCard {

	public Scriptorium(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Scriptorium";
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Courthouse", "Library"};
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.PAPER};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.TABLET);
	}
}
