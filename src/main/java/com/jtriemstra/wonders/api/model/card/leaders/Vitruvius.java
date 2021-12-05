package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.CommerceCard;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Vitruvius extends LeaderCard {

	@Override
	public String getName() {
		return "Vitruvius";
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		
		player.registerEvent("play.free", p -> p.gainCoins(2));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}

	@Override
	public String getHelp() {
		return "This card gives you 2 coins every time you play a card for free based on a card you have played before";
	}	
}
