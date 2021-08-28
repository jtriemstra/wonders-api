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

import com.jtriemstra.wonders.api.model.GeneralBeanFactory.BoardManagerFactory;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.Giza;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

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
		DiscardPile discard;
		
		@Autowired
		GameFactory testGameFactory;
		
		@Autowired
		@Qualifier("createNamedBoardStrategy")
		private BoardStrategy boardStrategy;

		@Autowired
		private BoardManagerFactory boardManagerFactory;
		
		@Bean
		@Scope("prototype")
		Game testGame(@Autowired @Qualifier("spyPlayerList") PlayerList realPlayerList) {
			CardFactory guildFactory = new GuildCardFactoryBasic();
			DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
			GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
			BoardSource boardSource = new BoardSourceBasic();
			BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
			
			return new Game("test", 3, new Ages(), new PostTurnActions(), new PostTurnActions(), discard, realPlayerList, new GameFlow(phaseFactory), boardManager);
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
			CardFactory guildFactory = new GuildCardFactoryBasic();
			DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
			GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
			BoardSource boardSource = new BoardSourceBasic();
			BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
			
			Game sourceGame = testGameFactory.createGame("spy1", 3, new GameFlow(phaseFactory), boardManager);
			Game spy = Mockito.spy(sourceGame);
			
			return spy;
		}
		
		@Bean
		@Scope("prototype")
		@Primary
		PlayerList spyPlayerList(@Autowired @Qualifier("playerList") PlayerList realPlayerList) {
			PlayerList spy = Mockito.spy(realPlayerList);
			Mockito.when(spy.size()).thenReturn(10);
			return spy;
		}
		
	}
}
