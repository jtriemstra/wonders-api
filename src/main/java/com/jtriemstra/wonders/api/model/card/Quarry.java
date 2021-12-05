package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Quarry extends NaturalResourceCard {
	
	public Quarry(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Quarry";
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.STONE), true);
		player.addResourceProvider(() -> new ResourceSet(ResourceType.STONE), true);
	}

	@Override
	public int getCoinCost() {
		return 1;
	}

	@Override
	public String getHelp() {
		return "This card adds 2 stones to the resources that are available to you each turn.";
	}
}
