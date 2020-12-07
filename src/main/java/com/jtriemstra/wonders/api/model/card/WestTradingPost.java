package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
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
	public void play(Player player, Game game) {
		super.play(player, game);
		player.addTradingProvider(new NaturalTradingProvider(CardDirection.LEFT));
	}

	@Override
	public String[] getFreebies() {
		return new String[] {"Forum"};
	}
	
	
}
