package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Circus extends ArmyCard {

	public Circus(int minPlayers, int age) {
		super(minPlayers, age, 3);
	}
	
	@Override
	public String getName() {
		return "Circus";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.STONE, ResourceType.STONE, ResourceType.ORE, ResourceType.STONE};
	}
}
