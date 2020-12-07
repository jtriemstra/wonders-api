package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public abstract class ScienceCard extends Card {
	public ScienceCard(int minPlayers, int age) {
		super(minPlayers, age);
	}

	public abstract Science getScience(); 

	@Override
	public String getType() {
		return "science";
	}

	@Override
	public void play(Player player, Game game) {
		player.addScienceProvider(() -> {return getScience();});
	}
}
