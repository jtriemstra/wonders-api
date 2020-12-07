package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jtriemstra.wonders.api.model.card.Card;

@Component
@Scope("prototype")
public class DiscardPile {

	private List<Card> cards = new ArrayList<>();
	
	public void add(Card[] all) {
		for (Card c : all) {
			cards.add(c);
		}
	}

	public void add(Card c) {
		cards.add(c);
	}
	
	public Card[] getCards() {
		Card[] result = new Card[cards.size()];
		for (int i=0; i<cards.size(); i++) {
			result[i] = cards.get(i);
		}
		return result;
	}

	public Card remove(String cardName) {
		Card result = null;
		int index = 0;
		for (Card c : cards) {
			if (c.getName().equals(cardName)) {
				result = c;
				break;
			}
			index++;
		}
		
		if (result == null) {
			throw new RuntimeException("card not found in discard");
		}
		
		cards.remove(index);
		return result;
	}

}
