package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class ForestCave extends NaturalResourceCard {
	
	public ForestCave(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Forest Cave";
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}
	
	@Override
	public void play(Player player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.WOOD, ResourceType.ORE), true);		
	}
	
	@Override
	public String getHelp() {
		return "This card serves as a 'wild card' for wood or ore - it can be used as one of those on each turn.";
	}
}
