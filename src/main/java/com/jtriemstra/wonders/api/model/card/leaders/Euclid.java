package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Science;
import com.jtriemstra.wonders.api.model.card.ScienceType;

public class Euclid extends LeaderCard {
		
	@Override
	public String getName() {
		return "Euclid";
	}
	
	@Override
	public void play(Player player, Game game) {
		player.addScienceProvider(() -> {return new Science(ScienceType.COMPASS);});
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 5;
	}
}
