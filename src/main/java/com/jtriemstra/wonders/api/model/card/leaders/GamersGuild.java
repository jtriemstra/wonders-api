package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.GuildCard;
import com.jtriemstra.wonders.api.model.card.provider.LambdaVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class GamersGuild extends GuildCard {

	private PlayerSourceStrategy playerSource = new PlayerOnlyStrategy();
	
	public GamersGuild(int minPlayers, int age) {
		super(minPlayers, age);
	}

	@Override
	public String getName() {
		return "Gamers Guild";
	}

	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new LambdaVPProvider(1,players, p -> {return p.getCoins() / 3;}, VictoryPointType.LEADER));
		
		super.play(player, game);
	}

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.WOOD, ResourceType.STONE, ResourceType.BRICK, ResourceType.ORE};
	}
}
