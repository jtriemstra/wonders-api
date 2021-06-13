package com.jtriemstra.wonders.api.model.card;

public abstract class CommerceCard extends Card {
	public CommerceCard(int minPlayers, int age) {
		super(minPlayers, age);
	}

	@Override
	public String getType() {
		return "commerce";
	}
}
