package com.jtriemstra.wonders.api.model.deck;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultDeckFactory implements DeckFactory {
	
	private CardFactory ageCardFactory;
	private CardFactory guildCardFactory;

	@Override
	public AgeDeck getDeck(int numberOfPlayer, int age) {
		return new AgeDeck(numberOfPlayer, age, ageCardFactory, guildCardFactory);
	}

}
