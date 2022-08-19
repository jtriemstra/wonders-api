package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class TimberYard extends NaturalResourceCard {
	
	public TimberYard(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Timber Yard";
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.STONE, ResourceType.WOOD), true);
	}
	
	@Override
	public String getHelp() {
		return "This card serves as a 'wild card' for wood or stone - it can be used as one or the other of those on each turn.";
	}
}
