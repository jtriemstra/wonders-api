package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // this is to support repeated calls to the BoardFactory from the injected Game. could also just create a new Game in every test
@Import(TestBase.TestConfig.class)
public class GameTests extends TestBase {
	
	@Autowired
	PlayerFactory playerFactory;
	
	@Autowired
	@Qualifier("testGame")
	Game game;
	
	@Test
	public void can_create_game() {
		Game g = gameFactory.createGame("test-game", boardFactory);
		assertNotNull(g);
		assertEquals("test-game", g.getName());
	}
	
	@Test
	public void can_add_players() {
		Player p1 = Mockito.mock(Player.class);
		Player p2 = Mockito.mock(Player.class);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		
		assertEquals(2, game.getNumberOfPlayers());
	}
	
	@Test
	public void when_adding_players_board_is_not_null() {
		Player p1 = playerFactory.createPlayer("test1");
		Player p2 = playerFactory.createPlayer("test2");
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		
		Assertions.assertNotNull(p1.getBoardName());
		Assertions.assertNotEquals("", p1.getBoardName());
	}
			
	@Test
	public void when_starting_game_current_age_increments() {
		Player p1 = playerFactory.createPlayer("test1");
		Player p2 = Mockito.mock(Player.class);
		Player p3 = Mockito.mock(Player.class);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		
		assertEquals(0, game.getCurrentAge());
		
		game.startAge();
		
		assertEquals(1, game.getCurrentAge());
	}

	@Test
	public void when_starting_game_player_has_seven_cards() {
		Player p1 = playerFactory.createPlayer("test1");
		Player p2 = Mockito.mock(Player.class);
		Player p3 = Mockito.mock(Player.class);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		
		assertEquals(0, p1.getHandSize());
		
		game.startAge();
		
		assertEquals(7, p1.getHandSize());
	}
	
	@Test
	public void when_adding_player_can_get_by_name() {
		Player p1 = playerFactory.createPlayer("test-new-player");
		game.addPlayer(p1);
		
		Player p1b = game.getPlayer("test-new-player");
		
		Assertions.assertNotNull(p1b);
	}
	
	@Test
	public void when_adding_player_get_resource_from_board() {
		Player p1 = playerFactory.createPlayer("test-new-player");
		game.addPlayer(p1);
		
		Assertions.assertEquals(1, p1.getResources(true).size());
		Assertions.assertEquals(ResourceType.PAPER, p1.getResources(true).get(0).getSingle());
	}
	
	
	@TestConfiguration
	static class TestConfig {

		@Autowired
		@Qualifier("createNamedBoardFactory")
		private BoardFactory boardFactory;
		
		@Bean
		@Scope("prototype")
		Game testGame() {
			return new Game("test", boardFactory, new Ages(), new DefaultDeckFactory(new AgeCardFactory(), new GuildCardFactoryBasic()), new PostTurnActions(), new PostTurnActions());
		}
		
		
	}
}
