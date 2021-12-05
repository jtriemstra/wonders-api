package com.jtriemstra.wonders.api.model.card;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.NeighborsOnlyStrategy;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class SpiesGuild extends GuildCard {
	
	private PlayerSourceStrategy playerSource;

	public SpiesGuild(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new NeighborsOnlyStrategy();
	}

	@Override
	public String getName() {
		return "Spies Guild";
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		List<IPlayer> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new CardVPProvider(1, ArmyCard.class, players, VictoryPointType.GUILD));
		
		super.play(player, game);
	}
	

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.BRICK,ResourceType.BRICK,ResourceType.BRICK,ResourceType.GLASS};
	}
	
	@Override
	public String getHelp() {
		return "This card gives you 1 point for each red card that your neighbors have played.";
	}

	
}
