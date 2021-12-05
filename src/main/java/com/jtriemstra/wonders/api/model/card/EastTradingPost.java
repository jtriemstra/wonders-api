package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.NaturalTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;


public class EastTradingPost extends CommerceCard {
	
	public EastTradingPost(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "East Trading Post";
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		super.play(player, game);
		player.addTradingProvider(new NaturalTradingProvider(CardDirection.RIGHT));
	}

	@Override
	public String[] getFreebies() {
		return new String[] {"Forum"};
	}

	@Override
	public String getHelp() {
		return "This card allows you to purchse brown card resources (wood, brick, ore, stone) from the neighbor to your right for 1 coin instead of 2.";
	}
}
