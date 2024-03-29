package com.jtriemstra.wonders.api.model.card;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.CardCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.CoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Arena extends CommerceCard {
	
	private PlayerSourceStrategy playerSource;
	
	public Arena(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new PlayerOnlyStrategy();
	}
	
	@Override
	public String getName() {
		return "Arena";
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] { ResourceType.STONE, ResourceType.STONE, ResourceType.ORE};  
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		List<IPlayer> players = playerSource.getPlayers(player, game);
		StageCoinProvider x = new StageCoinProvider(3, players);
		player.gainCoins(x.getCoins());
		player.addVPProvider(new StageVPProvider(1, players, VictoryPointType.COMMERCE));
		super.play(player, game);
	}
	
	@Override
	public String getHelp() {
		return "This card gives you 3 coins immediately for each wonder stage you have already built, and 1 point at the end of the game for each wonder stage you build during the entire game.";
	}
}
