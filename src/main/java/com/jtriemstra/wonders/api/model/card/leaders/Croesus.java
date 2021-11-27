package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;

public class Croesus extends LeaderCard {
		
	@Override
	public String getName() {
		return "Croesus";
	}
	
	@Override
	public void play(Player player, Game game) {
		player.gainCoins(6);
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}

	@Override
	public String getHelp() {
		return "This card grants 6 coins";
	}
}
