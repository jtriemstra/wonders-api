package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Barracks extends ArmyCard {

	public Barracks(int minPlayers, int age) {
		super(minPlayers, age, 1);
	}
	
	@Override
	public String getName() {
		return "Barracks";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.ORE};
	}
}
