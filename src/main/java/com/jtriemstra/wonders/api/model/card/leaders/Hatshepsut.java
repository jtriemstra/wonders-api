package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;

public class Hatshepsut extends LeaderCard {

	@Override
	public String getName() {
		return "Hatshepsut";
	}
	
	@Override
	public void play(Player player, Game game) {
		
		player.registerEvent("trade.neighbor", p -> p.addCoinProvider(new SimpleCoinProvider(1)));
		
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
