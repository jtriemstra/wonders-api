package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Lodge extends ScienceCard {

	public Lodge(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Lodge";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK, ResourceType.TEXTILE, ResourceType.PAPER};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.COMPASS);
	}
}
