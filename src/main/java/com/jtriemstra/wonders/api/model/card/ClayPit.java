package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class ClayPit extends NaturalResourceCard {
	
	public ClayPit(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Clay Pit";
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}

	@Override
	public void play(IPlayer player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.BRICK, ResourceType.ORE), true);
	}

	@Override
	public String getHelp() {
		return "This card serves as a 'wild card' for ore or brick - it can be used as one or the other of those on each turn.";
	}
}
