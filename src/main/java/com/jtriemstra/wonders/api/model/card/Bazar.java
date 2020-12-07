package com.jtriemstra.wonders.api.model.card;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerAndNeighborsStrategy;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.CardCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.CoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Bazar extends CommerceCard {
	
	private PlayerSourceStrategy playerSource;
	
	public Bazar(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new PlayerAndNeighborsStrategy();
	}
	
	@Override
	public String getName() {
		return "Bazar";
	}
		
	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.setCoinProvider(new CardCoinProvider(2, TechResourceCard.class, players));
		super.play(player, game);
	}
}
