package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Mine extends NaturalResourceCard {
	
	public Mine(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Mine";
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}

	@Override
	public void play(IPlayer player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.STONE, ResourceType.ORE), true);
	}

	@Override
	public String getHelp() {
		return "This card serves as a 'wild card' for stone or ore - it can be used as one of those on each turn.";
	}
}
