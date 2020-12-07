package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Walls extends ArmyCard {

	public Walls(int minPlayers, int age) {
		super(minPlayers, age, 2);
	}
	
	@Override
	public String getName() {
		return "Walls";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.STONE, ResourceType.STONE, ResourceType.STONE};
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Fortifications"};
	}
}
