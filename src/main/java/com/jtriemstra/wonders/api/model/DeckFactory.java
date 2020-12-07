package com.jtriemstra.wonders.api.model;

public interface DeckFactory {
	public Deck getDeck(int numberOfPlayer, int age);
}
