package com.jtriemstra.wonders.api.model.card;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.NeighborsOnlyStrategy;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class PhilosophersGuild extends GuildCard {
	
	private PlayerSourceStrategy playerSource;

	public PhilosophersGuild(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new NeighborsOnlyStrategy();
	}

	@Override
	public String getName() {
		return "Philosophers Guild";
	}
	
	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new CardVPProvider(1, ScienceCard.class, players, VictoryPointType.GUILD));
		
		super.play(player, game);
	}
	

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.BRICK,ResourceType.BRICK,ResourceType.BRICK,ResourceType.PAPER,ResourceType.TEXTILE};
	}

	
}
