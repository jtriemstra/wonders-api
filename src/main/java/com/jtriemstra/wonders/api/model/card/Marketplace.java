package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.TechTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;

public class Marketplace extends CommerceCard {
	
	public Marketplace(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Marketplace";
	}

	@Override
	public void play(IPlayer player, Game game) {
		super.play(player, game);
		player.addTradingProvider(new TechTradingProvider(CardDirection.BOTH));
	}

	@Override
	public String[] getFreebies() {
		return new String[] {"Caravansery"};
	}

	@Override
	public String getHelp() {
		return "This card allows you to purchse gray card resources (glass, cloth, paper) from either neighbor for 1 coin instead of 2.";
	}
}
