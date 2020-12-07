package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class ArcheryRange extends ArmyCard {

	public ArcheryRange(int minPlayers, int age) {
		super(minPlayers, age, 2);
	}
	
	@Override
	public String getName() {
		return "Archery Range";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD, ResourceType.ORE};
	}
	
}
