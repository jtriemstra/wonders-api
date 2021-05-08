package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.ArmyCard;
import com.jtriemstra.wonders.api.model.playrules.leaders.DiscountByType;

public class Leonidas extends LeaderCard {

	@Override
	public String getName() {
		return "Leonidas";
	}

	@Override
	public void play(Player player, Game game) {
		player.addPlayRule(new DiscountByType(ArmyCard.class));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 2;
	}
}
