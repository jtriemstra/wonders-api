package com.jtriemstra.wonders.api.model.deck.leaders;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.Deck;

import lombok.AllArgsConstructor;

public class LeaderDeck extends Deck {
	
	private CardFactory cardFactory;
	
	public LeaderDeck(CardFactory cardFactory) {
		this.cardFactory = cardFactory;
		Card[] cards = this.cardFactory.getAllCards();
		
		for (Card c : cards) {
			deck.add(c);
		}
	}

}
