package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.GuildCard;
import com.jtriemstra.wonders.api.model.card.GuildCard;
import com.jtriemstra.wonders.api.model.playrules.leaders.FreeByType;

public class Ramses extends LeaderCard {

	@Override
	public String getName() {
		return "Ramses";
	}

	@Override
	public void play(Player player, Game game) {
		player.addPlayRule(new FreeByType(GuildCard.class));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 5;
	}
}
