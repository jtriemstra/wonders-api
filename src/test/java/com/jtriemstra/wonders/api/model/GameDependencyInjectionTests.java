package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

import org.junit.jupiter.api.Test;
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

import com.jtriemstra.wonders.api.model.GeneralBeanFactory.PlayerListFactory;
import com.jtriemstra.wonders.api.model.board.BoardSide;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameDependencyInjectionTests {
		
	@Autowired
	@Qualifier("basicGame")
	Game game;
	
	@Autowired
	@Qualifier("mockGame")
	Game mockGame;
	
	@Autowired
	@Qualifier("spyGame")
	Game spyGame;
		
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
	static class TestConfig1 {
		
		@Autowired
		GameFactory gameFactory;
				
		
		@Bean
		@Scope("prototype")
		@Primary
		PlayerListFactory spyPlayerListFactory() {
			return () -> {
				PlayerList spy = Mockito.spy(new PlayerList());
				Mockito.when(spy.size()).thenReturn(10);
				return spy;
			};
		}
		
		@Bean
		@Scope("prototype")
		Game mockGame() {
			Game g = Mockito.mock(Game.class);
			
			return g;
		}
		
		@Bean
		@Scope("prototype")
		Game spyGame() {
			
			Game sourceGame = gameFactory.createGame("test1", 3, false, BoardSide.A_OR_B, false);
			Game spy = Mockito.spy(sourceGame);
			
			return spy;
		}

		@Bean
		@Scope("prototype")
		public Game basicGame() {
			return gameFactory.createGame("test2", 3, false, BoardSide.A_OR_B, false);
		}
	}
}
