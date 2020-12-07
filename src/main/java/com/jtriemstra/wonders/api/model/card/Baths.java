package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Baths extends VictoryCard {

	public Baths(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Baths";
	}
	
	@Override
	public SimpleVPProvider getVPProvider() {
		return new SimpleVPProvider(3, VictoryPointType.VICTORY);
	}

	@Override
	public String[] getFreebies() {
		return new String[] {"Aqueduct"};
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.STONE};
	}
}
