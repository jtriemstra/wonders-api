package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
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
	public void play(IPlayer player, Game game) {
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
	
	@Override
	public String getHelp() {
		return "This card serves as a 'wild card' for wood, ore, brick, or stone - it can be used as any one of those on each turn.";
	}
}
