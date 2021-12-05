package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;

public class Hatshepsut extends LeaderCard {

	@Override
	public String getName() {
		return "Hatshepsut";
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		
		player.registerEvent("trade.neighbor", p -> p.gainCoins(1));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 2;
	}

	@Override
	public String getHelp() {
		return "This card grants you a coin from the bank each time you purchase resources from a neighbor";
	}	
}
