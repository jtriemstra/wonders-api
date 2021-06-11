package com.jtriemstra.wonders.api.model.card;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerAndNeighborsStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class BuildersGuild extends GuildCard {
	
	private PlayerSourceStrategy playerSource;

	public BuildersGuild(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new PlayerAndNeighborsStrategy();
	}

	@Override
	public String getName() {
		return "Builders Guild";
	}
	
	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new StageVPProvider(1,players, VictoryPointType.GUILD));
		
		super.play(player, game);
	}
	

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.STONE,ResourceType.STONE,ResourceType.BRICK,ResourceType.BRICK,ResourceType.GLASS};
	}

	@Override
	public String getHelp() {
		return "This card gives you 1 victory point for each purple card played by you and your neighbors at the end of the game.";
	}
}
