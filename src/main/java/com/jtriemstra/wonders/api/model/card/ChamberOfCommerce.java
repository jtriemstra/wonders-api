package com.jtriemstra.wonders.api.model.card;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.CardCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.CoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class ChamberOfCommerce extends CommerceCard {
	
	private PlayerSourceStrategy playerSource;
	
	public ChamberOfCommerce(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new PlayerOnlyStrategy();
	}
	
	@Override
	public String getName() {
		return "Chamber of Commerce";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] { ResourceType.BRICK, ResourceType.BRICK, ResourceType.PAPER};  
	}
		
	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		CardCoinProvider x = new CardCoinProvider(2, TechResourceCard.class, players);
		player.gainCoins(x.getCoins());
		player.addVPProvider(new CardVPProvider(2, TechResourceCard.class, players, VictoryPointType.COMMERCE));
		super.play(player, game);
	}
	
	@Override
	public String getHelp() {
		return "This card gives you 2 coins immediately for each gray card you have already played, and 2 points at the end of the game for each gray card you have played during the entire game.";
	}
}
