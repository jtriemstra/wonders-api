package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Pantheon extends VictoryCard {

	public Pantheon(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Pantheon";
	}
	
	@Override
	public SimpleVPProvider getVPProvider() {
		return new SimpleVPProvider(7, VictoryPointType.VICTORY);
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK, ResourceType.GLASS, ResourceType.ORE, ResourceType.PAPER, ResourceType.TEXTILE};
	}

	
}
