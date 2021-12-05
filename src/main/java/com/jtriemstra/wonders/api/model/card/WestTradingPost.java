package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.NaturalTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;

public class WestTradingPost extends CommerceCard {
	
	public WestTradingPost(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "West Trading Post";
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		super.play(player, game);
		player.addTradingProvider(new NaturalTradingProvider(CardDirection.LEFT));
	}

	@Override
	public String[] getFreebies() {
		return new String[] {"Forum"};
	}

	@Override
	public String getHelp() {
		return "This card allows you to purchse brown card resources (wood, brick, ore, stone) from the neighbor to your left for 1 coin instead of 2.";
	}
	
	
}
