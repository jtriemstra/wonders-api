package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.Giza;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameDependencyInjectionTests {
	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	@Qualifier("testGame")
	Game game;
	
	@Autowired
	@Qualifier("mockGame")
	Game mockGame;
	
		@Autowired
		@Qualifier("spyGame")
		Game spyGame;
		
	@Autowired
	@Qualifier("createNamedBoardStrategy")
	private BoardStrategy boardStrategy;
		
	
	@Test
	public void when_using_factory_then_game_dependency_spies_are_injected() {
		Game g = gameFactory.createGame("test1");
		assertEquals(10, g.getNumberOfPlayers());
	}
	
	@Test
	public void when_using_autowire_then_game_dependency_spies_are_injected() {
		assertEquals(10, game.getNumberOfPlayers());
	}
	
	
	@Test
	public void when_using_mock_then_dependencies_get_bypassed() {
		assertEquals(0, mockGame.getNumberOfPlayers());
	}
	
	@Test
	public void when_using_spy_then_game_dependency_spies_are_injected() {
		assertEquals(10, spyGame.getNumberOfPlayers());
	}
		
	
	@TestConfiguration
	static class TestConfig {
		@Autowired
		GameFactory testGameFactory;
		
		@Autowired
		@Qualifier("createNamedBoardStrategy")
		private BoardStrategy boardStrategy;
		
		@Autowired
		@Qualifier("playerList")
		PlayerList realPlayerList;
		
		@Bean
		@Scope("prototype")
		Game testGame() {
			return new Game("test", boardStrategy, new Ages(), new DefaultDeckFactory(new AgeCardFactory(), new GuildCardFactoryBasic()), new PostTurnActions(), new PostTurnActions());
		}
		
		@Bean
		@Scope("prototype")
		Game mockGame() {
			Game g = Mockito.mock(Game.class);
			
			return g;
		}
		
		@Bean
		@Scope("prototype")
		Game spyGame(@Autowired BoardStrategy boardStrategyParam) {
			Game sourceGame = testGameFactory.createGame("spy1");
			Game spy = Mockito.spy(sourceGame);
			
			return spy;
		}
		
		@Bean
		@Scope("prototype")
		@Primary
		PlayerList spyPlayerList() {
			PlayerList spy = Mockito.spy(realPlayerList);
			Mockito.when(spy.size()).thenReturn(10);
			return spy;
		}
		
	}
}
