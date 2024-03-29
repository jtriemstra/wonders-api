package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtriemstra.wonders.api.model.card.Card;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DiscardPile {

	@JsonProperty("cards")
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

	public int[] getAges() {
		int[] result = new int[3];
		for (Card c : cards) {
			result[c.getAge() - 1] += 1;
		}
		return result;
	}
}
