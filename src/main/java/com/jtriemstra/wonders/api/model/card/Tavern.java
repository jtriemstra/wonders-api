package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;

public class Tavern extends CommerceCard {
	
	public Tavern(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Tavern";
	}
	
	@Override
	public void play(Player player, Game game) {
		player.setCoinProvider( new SimpleCoinProvider(5));
		super.play(player, game);
	}
}
