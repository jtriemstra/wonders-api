package com.jtriemstra.wonders.api.model.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class ArmyCardTests extends TestBase {
		
	@Test
	public void when_playing_stockade_get_one_army() {
		Card c = new Stockade(3,1);
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getArmies());
	}
	
	@Test
	public void when_playing_stockade_and_barracks_get_two_army() {
		Card c = new Stockade(3,1);
		Card c1 = new Barracks(3,1);
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, c1, g);
		
		Assertions.assertEquals(2, p1.getArmies());
	}
	
	@Test
	public void when_playing_arsenal_get_three_army() {
		Card c = new Arsenal(3,1);
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(3, p1.getArmies());
	}

}
