package com.jtriemstra.wonders.api.model.deck;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class DefaultDeckFactory implements DeckFactory {

	@Override
	public AgeDeck getDeck(int numberOfPlayer, int age) {
		return new AgeDeck(numberOfPlayer, age);
	}

}
