package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Arsenal extends ArmyCard {

	public Arsenal(int minPlayers, int age) {
		super(minPlayers, age, 3);
	}
	
	@Override
	public String getName() {
		return "Arsenal";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD, ResourceType.ORE, ResourceType.TEXTILE};
	}
}
