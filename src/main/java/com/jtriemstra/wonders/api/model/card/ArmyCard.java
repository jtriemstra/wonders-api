package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class ArmyCard extends Card {
	
	private int shields;
	
	public ArmyCard(int minPlayers, int age, int shields) {
		super(minPlayers, age);
		this.shields = shields;
	}

	@Override
	public void play(Player player, Game game) {
		super.play(player, game);
		player.addShields(shields);
	}
	
	@Override
	public String getType() {
		return "army";
	}
}
