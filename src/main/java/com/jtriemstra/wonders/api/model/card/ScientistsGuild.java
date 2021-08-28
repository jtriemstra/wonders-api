package com.jtriemstra.wonders.api.model.card;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.card.provider.CardCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;
import com.jtriemstra.wonders.api.model.phases.AgePhase;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class ScientistsGuild extends GuildCard {

	public ScientistsGuild(int minPlayers, int age) {
		super(minPlayers, age);
	}

	@Override
	public String getName() {
		return "Scientists Guild";
	}
	
	@Override
	public void play(Player player, Game game) {
		game.getFlow().addPostGameAction(player, new GetOptionsScience(), AgePhase.class);
	}
	
	@Override
	public ResourceType[] getResourceCost() {
		return new ResourceType[] {ResourceType.ORE,ResourceType.ORE,ResourceType.PAPER,ResourceType.WOOD,ResourceType.WOOD};
	}
	
	@Override
	public String getHelp() {
		return "This card lets you choose one additional science symbol to be combined with your green cards at the end of the game.";
	}

	
}
