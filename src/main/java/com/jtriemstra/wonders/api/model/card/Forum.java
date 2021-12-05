package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
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
	public void play(IPlayer player, Game game) {
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
	
	@Override
	public String getHelp() {
		return "This card serves as a 'wild card' for glass, cloth, or paper - it can be used as any one of those on each turn.";
	}
}
