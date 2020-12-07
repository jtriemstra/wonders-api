package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.jtriemstra.wonders.api.model.card.Card;

public class CardList implements Iterable<Card> {
	private List<Card> cards = new ArrayList<>();
	
	public void add(Card c) {
		cards.add(c);
	}
	
	public boolean contains(Card c) {
		for (Card c1 : cards) {
			if (c.getName().equals(c1.getName())) {
				return true;
			}
		}
		
		return false;
	}

	public Card get(String cardName) {
		for (Card c : cards) {
			if (cardName.equals(c.getName())){
				return c;
			}
		}
		
		throw new RuntimeException("card " + cardName + " not found");
	}

	@Override
	public Iterator<Card> iterator() {
		return cards.iterator();
	}

	public Card remove(String cardName) {
		int index;
		for (index=0; index < cards.size(); index++) {
			if (cards.get(index).getName().equals(cardName)) {
				break;
			}
		}
		
		if (index == cards.size()) {
			throw new RuntimeException("card " + cardName + " not found to remove");
		}
		
		Card c = cards.remove(index);
		return c;
	}

	public int size() {
		return cards.size();
	}

	public Card[] getAll() {
		return cards.toArray(new Card[cards.size()]);
	}

	public void clear() {
		cards.clear();
	}

	public List<Card> getByType(Class clazz) {
		return cards.stream().filter(c -> clazz.isInstance(c)).collect(Collectors.toList());
	}
}
