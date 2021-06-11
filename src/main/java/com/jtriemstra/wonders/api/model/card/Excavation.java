package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Excavation extends NaturalResourceCard {
	
	public Excavation(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Excavation";
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}
	
	@Override
	public void play(Player player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.STONE, ResourceType.BRICK), true);
	}

	@Override
	public String getHelp() {
		return "This card serves as a 'wild card' for stone or brick - it can be used as one of those on each turn.";
	}
}
