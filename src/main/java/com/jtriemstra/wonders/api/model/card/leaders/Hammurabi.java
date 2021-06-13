package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.VictoryCard;
import com.jtriemstra.wonders.api.model.playbuildrules.leaders.DiscountByType;

public class Hammurabi extends LeaderCard {

	@Override
	public String getName() {
		return "Hammurabi";
	}

	@Override
	public void play(Player player, Game game) {
		player.addPlayRule(new DiscountByType(VictoryCard.class));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 2;
	}

	@Override
	public String getHelp() {
		return "This card allows you to play a blue card for one less resource than shown on the card.";
	}
}
