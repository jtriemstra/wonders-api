package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

import lombok.Data;

public class GuildCard extends Card {
	
	public GuildCard(int minPlayers, int age) {
		super(minPlayers, age);
	}

	@Override
	public String getType() {
		return "guild";
	}
}
