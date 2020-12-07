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
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.setCoinProvider(new StageCoinProvider(3, players));
		player.addVPProvider(new StageVPProvider(1, players, VictoryPointType.COMMERCE));
		super.play(player, game);
	}
}
