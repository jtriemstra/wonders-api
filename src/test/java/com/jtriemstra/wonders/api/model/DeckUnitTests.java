package com.jtriemstra.wonders.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.AgeDeck;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;

public class DeckUnitTests {
	
	CardFactory ageCards = new AgeCardFactory();
	CardFactory guildCards = new GuildCardFactoryBasic();
	
	@Test
	public void when_creating_deck_three_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(3,1,ageCards,guildCards);
		Assertions.assertEquals(21, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_four_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(4,1,ageCards,guildCards);
		Assertions.assertEquals(28, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_five_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(5,1,ageCards,guildCards);
		Assertions.assertEquals(35, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_six_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(6,1,ageCards,guildCards);
		Assertions.assertEquals(42, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_seven_player_age_one_no_errors() {
		AgeDeck deck = new AgeDeck(7,1,ageCards,guildCards);
		Assertions.assertEquals(49, deck.allCards().size());
	}

	@Test
	public void when_creating_deck_three_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(3,2,ageCards,guildCards);
		Assertions.assertEquals(21, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_four_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(4,2,ageCards,guildCards);
		Assertions.assertEquals(28, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_five_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(5,2,ageCards,guildCards);
		Assertions.assertEquals(35, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_six_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(6,2,ageCards,guildCards);
		Assertions.assertEquals(42, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_seven_player_age_two_no_errors() {
		AgeDeck deck = new AgeDeck(7,2,ageCards,guildCards);
		Assertions.assertEquals(49, deck.allCards().size());
	}

	@Test
	public void when_creating_deck_three_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(3,3,ageCards,guildCards);
		Assertions.assertEquals(21, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_four_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(4,3,ageCards,guildCards);
		Assertions.assertEquals(28, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_five_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(5,3,ageCards,guildCards);
		Assertions.assertEquals(35, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_six_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(6,3,ageCards,guildCards);
		Assertions.assertEquals(42, deck.allCards().size());
	}
	
	@Test
	public void when_creating_deck_seven_player_age_three_no_errors() {
		AgeDeck deck = new AgeDeck(7,3,ageCards,guildCards);
		Assertions.assertEquals(49, deck.allCards().size());
	}
}
