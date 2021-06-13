package com.jtriemstra.wonders.api.model.card;

public abstract class ResourceCard extends Card {

	public ResourceCard(int minPlayers, int age) {
		super(minPlayers, age);
	}

	@Override
	public String getType() {
		return "resource";
	}
}
