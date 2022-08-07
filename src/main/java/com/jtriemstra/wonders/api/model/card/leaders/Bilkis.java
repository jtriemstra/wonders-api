package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.playbuildrules.leaders.BilkisTrading;

public class Bilkis extends LeaderCard {

	@Override
	public String getName() {
		return "Bilkis";
	}

	@Override
	public void play(IPlayer player, Game game) {
		player.addPlayRule(new BilkisTrading());
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 4;
	}

	@Override
	public String getHelp() {
		return "This card allows you to purchase 1 resource each turn from the bank";
	}
}
