package com.jtriemstra.wonders.api.model.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtriemstra.wonders.api.model.card.Card;

public class Deck {
	protected List<Card> deck = new ArrayList<Card>();
	private Logger logger3 = LoggerFactory.getLogger("tests");

	public Card draw() {
		Random r = new Random();
		Card c =  deck.remove(r.nextInt(deck.size()));
		logger3.info("new " + c.getClass().getName() + "(3,1),");
		return c;
	}
	
	public List<Card> allCards() {
		return deck;
	}
}
