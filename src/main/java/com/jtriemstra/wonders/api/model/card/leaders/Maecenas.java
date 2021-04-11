package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.playrules.leaders.FreeByType;

public class Maecenas extends LeaderCard {

	@Override
	public String getName() {
		return "Maecenas";
	}

	@Override
	public void play(Player player, Game game) {
		player.addPlayRule(new FreeByType(LeaderCard.class));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}
}
