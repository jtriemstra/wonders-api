package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
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
	public void play(IPlayer player, Game game) {
		player.gainCoins(5);
		super.play(player, game);
	}
	
	@Override
	public String getHelp() {
		return "This card gives you 5 coins immediately.";
	}
	
}
