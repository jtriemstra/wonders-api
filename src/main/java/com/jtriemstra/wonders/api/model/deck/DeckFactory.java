package com.jtriemstra.wonders.api.model.deck;

public interface DeckFactory {
	public AgeDeck getDeck(int numberOfPlayer, int age);
}
