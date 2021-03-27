package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Science;
import com.jtriemstra.wonders.api.model.card.ScienceType;

public class Pythagoras extends LeaderCard {
		
	@Override
	public String getName() {
		return "Pythagoras";
	}
	
	@Override
	public void play(Player player, Game game) {
		player.addScienceProvider(() -> {return new Science(ScienceType.GEAR);});
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}
}
