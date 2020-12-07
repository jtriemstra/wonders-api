package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
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
	public void play(Player player, Game game) {
		super.play(player, game);
		player.addTradingProvider(new NaturalTradingProvider(CardDirection.RIGHT));
	}

	@Override
	public String[] getFreebies() {
		return new String[] {"Forum"};
	}
}
