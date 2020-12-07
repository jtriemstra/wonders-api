package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class SiegeWorkshop extends ArmyCard {

	public SiegeWorkshop(int minPlayers, int age) {
		super(minPlayers, age, 3);
	}
	
	@Override
	public String getName() {
		return "Siege Workshop";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK, ResourceType.BRICK, ResourceType.WOOD};
	}
}
