package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.ArmyCard;
import com.jtriemstra.wonders.api.model.playbuildrules.leaders.DiscountByType;

public class Leonidas extends LeaderCard {

	@Override
	public String getName() {
		return "Leonidas";
	}

	@Override
	public void play(IPlayer player, Game game) {
		player.addPlayRule(new DiscountByType(ArmyCard.class));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 2;
	}

	@Override
	public String getHelp() {
		return "This card allows you to play a red card for one less resource than shown on the card.";
	}
}
