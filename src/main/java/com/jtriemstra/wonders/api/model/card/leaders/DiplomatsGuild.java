package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.NeighborsOnlyStrategy;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.GuildCard;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class DiplomatsGuild extends GuildCard {

	private PlayerSourceStrategy playerSource = new NeighborsOnlyStrategy();
	
	public DiplomatsGuild(int minPlayers, int age) {
		super(minPlayers, age);
	}

	@Override
	public String getName() {
		return "Diplomats Guild";
	}

	@Override
	public void play(IPlayer player, Game game) {
		List<IPlayer> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new CardVPProvider(1, LeaderCard.class, players, VictoryPointType.GUILD));
		
		super.play(player, game);
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.WOOD, ResourceType.STONE, ResourceType.GLASS, ResourceType.PAPER};
	}

	@Override
	public String getHelp() {
		return "This card grants 1 victory point for each Leader card your neighbors have played";
	}
}
