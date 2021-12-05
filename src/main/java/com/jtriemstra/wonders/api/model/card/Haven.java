package com.jtriemstra.wonders.api.model.card;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.CardCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Haven extends CommerceCard {
	
	private PlayerSourceStrategy playerSource;
	
	public Haven(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new PlayerOnlyStrategy();
	}
	
	@Override
	public String getName() {
		return "Haven";
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] { ResourceType.WOOD, ResourceType.ORE, ResourceType.TEXTILE};  
	}
		
	@Override
	public void play(IPlayer player, Game game) {
		List<IPlayer> players = playerSource.getPlayers(player, game);
		CardCoinProvider x = new CardCoinProvider(1, NaturalResourceCard.class, players);
		player.gainCoins(x.getCoins());
		player.addVPProvider(new CardVPProvider(1, NaturalResourceCard.class, players, VictoryPointType.COMMERCE));
		super.play(player, game);
	}
	
	@Override
	public String getHelp() {
		return "This card gives you 1 coin immediately for each brown card you have already played, and 1 point at the end of the game for each brown card you have when the game ends.";
	}
}
