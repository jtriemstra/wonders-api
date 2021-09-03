package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.CommerceCard;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Nero extends LeaderCard {

	@Override
	public String getName() {
		return "Nero";
	}
	
	@Override
	public void play(Player player, Game game) {
		
		player.registerEvent("conflict.victory", p -> p.addCoinProvider(new SimpleCoinProvider(2)));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}

	@Override
	public String getHelp() {
		return "This card grants you 2 coins for every conflict at the end of an age where you are victorious";
	}	
}
