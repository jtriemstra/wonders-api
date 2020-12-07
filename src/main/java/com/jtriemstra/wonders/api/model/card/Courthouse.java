package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Courthouse extends VictoryCard {

	public Courthouse(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Courthouse";
	}
	
	@Override
	public SimpleVPProvider getVPProvider() {
		return new SimpleVPProvider(4, VictoryPointType.VICTORY);
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK, ResourceType.TEXTILE};
	}
}
