package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class GuardTower extends ArmyCard {

	public GuardTower(int minPlayers, int age) {
		super(minPlayers, age, 1);
	}
	
	@Override
	public String getName() {
		return "Guard Tower";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.BRICK};
	}
}
