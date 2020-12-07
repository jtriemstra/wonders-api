package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class TrainingGround extends ArmyCard {

	public TrainingGround(int minPlayers, int age) {
		super(minPlayers, age, 2);
	}
	
	@Override
	public String getName() {
		return "Training Ground";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.WOOD, ResourceType.ORE, ResourceType.ORE};
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Circus"};
	}
}
