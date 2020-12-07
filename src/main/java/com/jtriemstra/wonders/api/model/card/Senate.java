package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Senate extends VictoryCard {

	public Senate(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Senate";
	}
	
	@Override
	public SimpleVPProvider getVPProvider() {
		return new SimpleVPProvider(6, VictoryPointType.VICTORY);
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.STONE, ResourceType.WOOD, ResourceType.WOOD, ResourceType.ORE};
	}

	
}
