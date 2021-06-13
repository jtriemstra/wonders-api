package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.NeighborsOnlyStrategy;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.GuildCard;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class ArchitectsGuild extends GuildCard {

	private PlayerSourceStrategy playerSource = new NeighborsOnlyStrategy();
	
	public ArchitectsGuild(int minPlayers, int age) {
		super(minPlayers, age);
	}

	@Override
	public String getName() {
		return "Architects Guild";
	}

	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new CardVPProvider(3, GuildCard.class, players, VictoryPointType.GUILD));
		
		super.play(player, game);
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.ORE, ResourceType.ORE, ResourceType.ORE, ResourceType.BRICK, ResourceType.PAPER, ResourceType.TEXTILE};
	}

	@Override
	public String getHelp() {
		return "This card grants 3 victory points for each purple card your neighbors have at the end of the game";
	}
}
