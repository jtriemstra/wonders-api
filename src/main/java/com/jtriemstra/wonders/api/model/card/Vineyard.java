package com.jtriemstra.wonders.api.model.card;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.CardCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.CoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Vineyard extends CommerceCard {
	
	public Vineyard(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Vineyard";
	}
	
	@Override
	public void play(Player player, Game game) {
		List<Player> players = new ArrayList<>();
		players.add(player);
		players.add(game.getLeftOf(player));
		players.add(game.getRightOf(player));
		
		player.setCoinProvider(new CardCoinProvider(1, NaturalResourceCard.class, players));
		super.play(player, game);
	}
}
