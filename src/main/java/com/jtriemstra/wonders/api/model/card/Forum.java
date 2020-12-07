package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Forum extends CommerceCard {
	
	public Forum(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Forum";
	}
	
	@Override
	public void play(Player player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.GLASS, ResourceType.PAPER, ResourceType.TEXTILE), false);
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK};
	}
	
	@Override
	public String[] getFreebies() {
		return new String[] {"Haven"};
	}
}
