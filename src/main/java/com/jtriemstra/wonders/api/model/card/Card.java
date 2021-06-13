package com.jtriemstra.wonders.api.model.card;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public abstract class Card {
	private int minPlayers;
	private int age;

	public String getName() {
		return "";
	}
	
	public abstract String getHelp();
	
	public abstract String getType();

	@JsonIgnore
	public int getMinPlayers() {
		return minPlayers;
	}

	//TODO: (low) possibly refactor this into a FreebieProvider structure if another use case comes up
	@JsonIgnore
	public String[] getFreebies() {
		return null;
	}

	@JsonIgnore
	public int getCoinCost() {
		return 0;
	}

	@JsonIgnore
	public ResourceType[] getResourceCost() {
		return null;
	}

	@JsonIgnore
	public int getAge() {
		return age;
	}
	
	public Card(int minPlayers, int age) {
		this.minPlayers = minPlayers;
		this.age = age;
	}
	
	public void play(Player player, Game game) {

	}
}
