package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ArmyCard extends Card {
	
	private int shields;
	
	public ArmyCard(int minPlayers, int age, int shields) {
		super(minPlayers, age);
		this.shields = shields;
	}

	@Override
	public void play(Player player, Game game) {
		super.play(player, game);
		log.info("adding " + shields + " shields");
		player.getArmyFacade().addShields(shields);
	}
	
	@Override
	public String getType() {
		return "army";
	}
	
	@Override
	public String getHelp() {
		return "Adds the number of shields shown to your army. Victory points for red cards are awarded at the end of each age.";
	}
}
