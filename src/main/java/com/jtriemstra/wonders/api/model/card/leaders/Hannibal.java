package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class Hannibal extends LeaderCard {
	
	@Override
	public String getName() {
		return "Hannibal";
	}
	
	@Override
	public int getCoinCost() {
		return 2;
	}
	
	@Override
	public void play(Player player, Game game) {
		super.play(player, game);
		player.addShields(1);
	}

	@Override
	public String getHelp() {
		return "This card grants 1 extra shield";
	}

	@Override
	public String getType() {
		return "army";
	}
}
