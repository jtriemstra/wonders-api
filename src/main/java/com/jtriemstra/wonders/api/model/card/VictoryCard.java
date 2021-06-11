package com.jtriemstra.wonders.api.model.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;

public abstract class VictoryCard extends Card {
	public VictoryCard(int minPlayers, int age) {
		super(minPlayers, age);
	}

	@JsonIgnore
	public abstract VictoryPointProvider getVPProvider();

	@Override
	public String getType() {
		return "victory";
	}

	@Override
	public void play(Player player, Game game) {
		player.addVPProvider(getVPProvider());;
	}
	
	@Override
	public String getHelp() {
		return "This card provides the number of points shown at the end of the game.";
	}
}
