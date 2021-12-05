package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.ScienceCard;
import com.jtriemstra.wonders.api.model.playbuildrules.leaders.DiscountByType;

public class Archimedes extends LeaderCard {

	@Override
	public String getName() {
		return "Archimedes";
	}

	@Override
	public void play(IPlayer player, Game game) {
		player.addPlayRule(new DiscountByType(ScienceCard.class));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 4;
	}

	@Override
	public String getHelp() {
		return "This card allows you to play a green card for one less resource than shown on the card.";
	}
}
