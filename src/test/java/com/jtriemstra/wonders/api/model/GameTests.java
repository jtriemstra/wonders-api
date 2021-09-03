package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.board.BoardSide;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // this is to support repeated calls to the BoardFactory from the injected Game. could also just create a new Game in every test
public class GameTests  {
	
	@Autowired
	PlayerFactory playerFactory;
	
	@Autowired @Qualifier("basicGame")
	Game basicGame;
		
	@Test
	public void can_add_players() {
		Player p1 = Mockito.mock(Player.class);
		Player p2 = Mockito.mock(Player.class);
		
		basicGame.addPlayer(p1);
		basicGame.addPlayer(p2);
		
		assertEquals(2, basicGame.getNumberOfPlayers());
	}
	
	@Test
	public void when_adding_players_board_is_not_null() {
		Player p1 = playerFactory.createPlayer("test1");
		Player p2 = playerFactory.createPlayer("test2");
		
		basicGame.addPlayer(p1);
		basicGame.addPlayer(p2);
		
		Assertions.assertNotNull(p1.getBoardName());
		Assertions.assertEquals("Ephesus", p1.getBoardName());
	}
	
	@Test
	public void when_adding_player_can_get_by_name() {
		Player p1 = playerFactory.createPlayer("test-new-player");
		basicGame.addPlayer(p1);
		
		Player p1b = basicGame.getPlayer("test-new-player");
		
		Assertions.assertNotNull(p1b);
	}
	
	@TestConfiguration
	public static class TestConfig {

		@Autowired
		private GameFactory gameFactory;

		@Autowired
		protected PlayerFactory playerFactory;
		
		@Bean
		public Game basicGame() {
			return gameFactory.createGame("test2", 3, false, BoardSide.A_OR_B, false);
		}
	}
}
