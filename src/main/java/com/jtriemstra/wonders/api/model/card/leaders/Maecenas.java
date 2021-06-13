package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.playbuildrules.leaders.FreeByType;

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

	@Override
	public String getHelp() {
		return "This card allows you to play any future leader cards for free";
	}
}
