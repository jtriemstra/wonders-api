package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class School extends ScienceCard {

	public School(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "School";
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Academy", "Study"};
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.PAPER, ResourceType.WOOD};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.TABLET);
	}
}
