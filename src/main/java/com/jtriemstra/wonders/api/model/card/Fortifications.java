package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Fortifications extends ArmyCard {

	public Fortifications(int minPlayers, int age) {
		super(minPlayers, age, 3);
	}
	
	@Override
	public String getName() {
		return "Fortifications";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.ORE, ResourceType.ORE, ResourceType.ORE, ResourceType.STONE};
	}
}
