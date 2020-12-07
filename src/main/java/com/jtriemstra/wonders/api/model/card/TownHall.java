package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class TownHall extends VictoryCard {

	public TownHall(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Town Hall";
	}
	
	@Override
	public SimpleVPProvider getVPProvider() {
		return new SimpleVPProvider(6, VictoryPointType.VICTORY);
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.STONE, ResourceType.STONE, ResourceType.GLASS, ResourceType.ORE};
	}

	
}
