package com.jtriemstra.wonders.api.model.deck;

import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.Aristotle;
import com.jtriemstra.wonders.api.model.card.leaders.Plato;

public class LeaderCardFactory implements CardFactory {
	public Card[] getAllCards() {
		return new Card[] {
			new Plato(),
			new Aristotle()
		};
	}
}
