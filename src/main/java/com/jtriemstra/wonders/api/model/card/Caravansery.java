package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Caravansery extends CommerceCard {
	
	public Caravansery(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Caravansery";
	}
	
	@Override
	public void play(Player player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.WOOD, ResourceType.ORE, ResourceType.BRICK, ResourceType.STONE), false);
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD};
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Lighthouse"};
	}
}
