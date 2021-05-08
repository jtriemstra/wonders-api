package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class Caesar extends LeaderCard {
	
	@Override
	public String getName() {
		return "Caesar";
	}
	
	@Override
	public int getCoinCost() {
		return 5;
	}
	
	@Override
	public void play(Player player, Game game) {
		super.play(player, game);
		player.addShields(2);
	}
	
}
