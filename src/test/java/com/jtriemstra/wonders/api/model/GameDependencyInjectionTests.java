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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
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
	@Qualifier("createNamedBoardFactory")
	private BoardFactory boardFactory;
	
	@Autowired
	@Qualifier("spyBoardFactory")
	private BoardFactory spyBoardFactory;
	
	@Test
	public void when_using_factory_then_game_dependency_spies_are_injected() {
		Game g = gameFactory.createGame("test1", boardFactory);
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
	
	@Test
	public void when_using_spy_board_factory_then_correct_result_returned() {
		Board b = spyBoardFactory.getBoard();
		Assertions.assertNotNull(b);
		Assertions.assertTrue(b instanceof Giza);
	}
	
	@Test
	public void when_adding_player_board_factory_is_called() {
		Game g = gameFactory.createGame("test1", spyBoardFactory);
		
		g.addPlayer(Mockito.mock(Player.class));
		
		Mockito.verify(spyBoardFactory, Mockito.times(1)).getBoard();
	}
	
	
	@TestConfiguration
	static class TestConfig {
		@Autowired
		GameFactory testGameFactory;
		
		@Autowired
		@Qualifier("createNamedBoardFactory")
		private BoardFactory boardFactory;
		
		@Autowired
		@Qualifier("playerList")
		PlayerList realPlayerList;
		
		@Bean
		@Scope("prototype")
		Game testGame() {
			return new Game("test", boardFactory, new Ages(), new DefaultDeckFactory(new AgeCardFactory(), new GuildCardFactoryBasic()), new PostTurnActions(), new PostTurnActions());
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
			Game sourceGame = testGameFactory.createGame("spy1", boardFactory);
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
		
		@Bean
		@Scope("prototype")
		@Primary
		BoardFactory spyBoardFactory() {
			BoardFactory spy = Mockito.spy(boardFactory);
			Mockito.doReturn(new Giza(true)).when(spy).getBoard();
			return spy;
		}
	}
}
