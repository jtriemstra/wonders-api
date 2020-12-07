package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Stockade extends ArmyCard {

	public Stockade(int minPlayers, int age) {
		super(minPlayers, age, 1);
	}
	
	@Override
	public String getName() {
		return "Stockade";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.WOOD};
	}
}
