package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Library extends ScienceCard {

	public Library(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Library";
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Senate", "University"};
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.TEXTILE, ResourceType.STONE, ResourceType.STONE};
	}
	
	@Override
	public Science getScience() {
		return new Science(ScienceType.TABLET);
	}
}
