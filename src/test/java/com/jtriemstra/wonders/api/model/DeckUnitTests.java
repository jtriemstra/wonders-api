package com.jtriemstra.wonders.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.model.deck.AgeDeck;

public class DeckUnitTests {
	
	@Test
	public void when_creating_deck_three_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(3,1);
		Assertions.assertEquals(21, deck.size());
	}
	
	@Test
	public void when_creating_deck_four_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(4,1);
		Assertions.assertEquals(28, deck.size());
	}
	
	@Test
	public void when_creating_deck_five_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(5,1);
		Assertions.assertEquals(35, deck.size());
	}
	
	@Test
	public void when_creating_deck_six_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(6,1);
		Assertions.assertEquals(42, deck.size());
	}
	
	@Test
	public void when_creating_deck_seven_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(7,1);
		Assertions.assertEquals(49, deck.size());
	}

	@Test
	public void when_creating_deck_three_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(3,2);
		Assertions.assertEquals(21, deck.size());
	}
	
	@Test
	public void when_creating_deck_four_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(4,2);
		Assertions.assertEquals(28, deck.size());
	}
	
	@Test
	public void when_creating_deck_five_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(5,2);
		Assertions.assertEquals(35, deck.size());
	}
	
	@Test
	public void when_creating_deck_six_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(6,2);
		Assertions.assertEquals(42, deck.size());
	}
	
	@Test
	public void when_creating_deck_seven_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(7,2);
		Assertions.assertEquals(49, deck.size());
	}

	@Test
	public void when_creating_deck_three_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(3,3);
		Assertions.assertEquals(21, deck.size());
	}
	
	@Test
	public void when_creating_deck_four_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(4,3);
		Assertions.assertEquals(28, deck.size());
	}
	
	@Test
	public void when_creating_deck_five_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(5,3);
		Assertions.assertEquals(35, deck.size());
	}
	
	@Test
	public void when_creating_deck_six_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(6,3);
		Assertions.assertEquals(42, deck.size());
	}
	
	@Test
	public void when_creating_deck_seven_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(7,3);
		Assertions.assertEquals(49, deck.size());
	}
}
