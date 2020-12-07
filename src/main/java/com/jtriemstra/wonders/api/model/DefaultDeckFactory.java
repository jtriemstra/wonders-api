package com.jtriemstra.wonders.api.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

public class DefaultDeckFactory implements DeckFactory {

	@Override
	public Deck getDeck(int numberOfPlayer, int age) {
		return new Deck(numberOfPlayer, age);
	}

}
