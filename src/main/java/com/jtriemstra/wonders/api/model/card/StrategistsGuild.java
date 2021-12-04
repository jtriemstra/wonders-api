package com.jtriemstra.wonders.api.model.card;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.NeighborsOnlyStrategy;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.LambdaVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class StrategistsGuild extends GuildCard {
	
	private PlayerSourceStrategy playerSource;

	public StrategistsGuild(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new NeighborsOnlyStrategy();
	}

	@Override
	public String getName() {
		return "Strategists Guild";
	}
	
	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new LambdaVPProvider(1,players, p -> {return p.getArmyFacade().getNumberOfDefeats();}, VictoryPointType.GUILD));
		
		super.play(player, game);
	}
	

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.ORE,ResourceType.ORE,ResourceType.STONE,ResourceType.TEXTILE};
	}
	
	@Override
	public String getHelp() {
		return "This card gives you 1 point for each defeat token that your neighbors have received.";
	}

	
}
