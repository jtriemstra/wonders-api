package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.CommerceCard;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Xenophon extends LeaderCard {

	@Override
	public String getName() {
		return "Xenophon";
	}
	
	@Override
	public void play(Player player, Game game) {
		
		player.registerEvent("play.commerce", p -> p.gainCoins(2));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 2;
	}	
}
