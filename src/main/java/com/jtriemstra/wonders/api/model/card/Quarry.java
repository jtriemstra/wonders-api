package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
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
	public void play(Player player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.STONE), true);
		player.addResourceProvider(() -> new ResourceSet(ResourceType.STONE), true);
	}

	@Override
	public int getCoinCost() {
		return 1;
	}
}
