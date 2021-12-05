package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;

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
	public void play(IPlayer player, Game game) {
		player.addScienceProvider(() -> {return getScience();});
	}
	
	@Override
	public String getHelp() {
		return "Science symbols are scored at the end of the game - 7 pts for each set of 3 unique symbols, and N-squared pts for the number of each symbol.";
	}
}
