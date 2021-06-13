package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.GuildCard;
import com.jtriemstra.wonders.api.model.card.GuildCard;
import com.jtriemstra.wonders.api.model.playbuildrules.leaders.FreeByType;

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

	@Override
	public String getHelp() {
		return "This card allows you to play purple cards for free, whether you have the resources or not";
	}
}
