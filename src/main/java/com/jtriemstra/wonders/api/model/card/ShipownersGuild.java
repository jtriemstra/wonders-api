package com.jtriemstra.wonders.api.model.card;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class ShipownersGuild extends GuildCard {
	
	private PlayerSourceStrategy playerSource;

	public ShipownersGuild(int minPlayers, int age) {
		super(minPlayers, age);
		playerSource = new PlayerOnlyStrategy();
	}

	@Override
	public String getName() {
		return "Shipowners Guild";
	}
	
	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new CardVPProvider(1, NaturalResourceCard.class, players, VictoryPointType.GUILD));
		player.addVPProvider(new CardVPProvider(1, TechResourceCard.class, players, VictoryPointType.GUILD));
		player.addVPProvider(new CardVPProvider(1, GuildCard.class, players, VictoryPointType.GUILD));
		
		super.play(player, game);
	}
	

	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.WOOD,ResourceType.WOOD,ResourceType.WOOD,ResourceType.GLASS, ResourceType.PAPER};
	}
	
	@Override
	public String getHelp() {
		return "This card gives you 1 point for each brown, gray, or purple card that you have played.";
	}

	
}
