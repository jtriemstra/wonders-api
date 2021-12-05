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

public class Lighthouse extends CommerceCard {
	
	private PlayerSourceStrategy playerSource;
	
	public Lighthouse(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new PlayerOnlyStrategy();
	}
	
	@Override
	public String getName() {
		return "Lighthouse";
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] { ResourceType.STONE, ResourceType.GLASS};  
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		List<IPlayer> players = playerSource.getPlayers(player, game);
		CardCoinProvider x = new CardCoinProvider(1, CommerceCard.class, players);
		player.gainCoins(x.getCoins());
		player.addVPProvider(new CardVPProvider(1, CommerceCard.class, players, VictoryPointType.COMMERCE));
		super.play(player, game);
	}
	
	@Override
	public String getHelp() {
		return "This card gives you 1 coin immediately for each yellow card you have already played, and 1 point at the end of the game for each yellow card you have when the game ends.";
	}
}
