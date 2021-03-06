package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GameInjectionTests {
	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	@Qualifier("testGame")
	Game game;
	
	//@MockBean //- @MockBean appears to hijack all the autowired instances of Game, not just the one with the annotation
	@Autowired
	@Qualifier("mockGame")
	Game mockGame;
	
	@Autowired
	@Qualifier("spyGame")
	Game spyGame;
	
	@Autowired
	@Qualifier("spyGame2")
	Game spyGame2;
	

	@Autowired
	@Qualifier("createNamedBoardStrategy")
	private BoardStrategy boardStrategy;
	
	@Test
	public void when_loading_test_autowire_succeeds() {
		assertNotNull(gameFactory);
		assertNotNull(game);
	}
	
	@Test
	public void when_using_factory_then_game_dependencies_are_injected() {
		Game g = gameFactory.createGame("test1", boardStrategy);
		assertEquals(0, g.getNumberOfPlayers());
	}

	@Test
	public void when_using_factory_then_name_is_correct() {
		Game g = gameFactory.createGame("test1", boardStrategy);
		assertEquals("test1", g.getName());
	}
	
	@Test
	public void when_using_autowire_then_game_dependencies_are_injected() {
		assertEquals(0, game.getNumberOfPlayers());
	}
	
	@Test
	public void when_using_autowire_then_name_is_correct() {
		assertEquals("test", game.getName());
	}
	
	@Test
	public void when_using_mock_then_get_mock_instance() {
		assertEquals("mock1", mockGame.getName());
	}
	
	@Test
	public void when_using_spy_then_mocked_method_and_source_method_are_correct() {
		assertEquals("spy1", spyGame.getName());
		assertEquals(-1, spyGame.getNumberOfPlayers());
	}
	
	@Test
	public void when_using_spy_then_dependencies_are_injected() {
		assertEquals(0, spyGame2.getNumberOfPlayers());
	}
	
	@TestConfiguration
	static class TestConfig {
		@Autowired
		GameFactory testGameFactory;
		
		@Autowired
		@Qualifier("createNamedBoardStrategy")
		private BoardStrategy boardStrategy;
		
		@Bean
		@Scope("prototype")
		Game testGame() {
			return new Game("test", boardStrategy, new Ages(), new DefaultDeckFactory(new AgeCardFactory(), new GuildCardFactoryBasic()), new PostTurnActions(), new PostTurnActions());
		}
		
		@Bean
		@Scope("prototype")
		Game mockGame() {
			Game g = Mockito.mock(Game.class);
			Mockito.when(g.getName()).thenReturn("mock1");
			return g;
		}
		
		@Bean
		@Scope("prototype")
		Game spyGame() {
			Game sourceGame = testGameFactory.createGame("spy1", boardStrategy);
			Game spy = Mockito.spy(sourceGame);
			
			Mockito.when(spy.getNumberOfPlayers()).thenReturn(-1);
			
			return spy;
		}
		
		@Bean
		@Scope("prototype")
		Game spyGame2() {
			Game sourceGame = testGameFactory.createGame("spy2", boardStrategy);
			Game spy = Mockito.spy(sourceGame);
						
			return spy;
		}
	}
}
