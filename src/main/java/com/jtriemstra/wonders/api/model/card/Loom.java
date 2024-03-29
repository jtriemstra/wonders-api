package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Loom extends TechResourceCard {
	
	public Loom(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Loom";
	}

	@Override
	public void play(IPlayer player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.TEXTILE), true);
	}

	@Override
	public String getHelp() {
		return "This card adds 1 cloth to the resources that are available to you each turn.";
	}
	
}
