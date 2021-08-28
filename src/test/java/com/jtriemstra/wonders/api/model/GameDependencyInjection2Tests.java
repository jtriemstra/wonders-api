package com.jtriemstra.wonders.api.model;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
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

import com.jtriemstra.wonders.api.model.Game.DiscardFinalCardAction;
import com.jtriemstra.wonders.api.model.Game.PlayCardsAction;
import com.jtriemstra.wonders.api.model.Game.ResolveCommerceAction;
import com.jtriemstra.wonders.api.model.Game.ResolveConflictAction;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.BoardManagerFactory;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.Board;
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
public class GameDependencyInjection2Tests {
	
	@Autowired
	GameFactory gameFactory;
		
	@Autowired
	@Qualifier("spyBoardStrategy")
	private BoardStrategy spyBoardStrategy;

	@Autowired
	private BoardManagerFactory boardManagerFactory;
	
	@Test
	public void when_using_spy_board_factory_then_correct_result_returned() {
		Board b = spyBoardStrategy.getBoard(new BoardSourceBasic(), BoardSide.A_ONLY, new HashSet<>());
		Assertions.assertNotNull(b);
		Assertions.assertTrue(b instanceof Giza);
	}
	
	@Test
	public void when_adding_player_board_factory_is_called() {
		CardFactory guildFactory = new GuildCardFactoryBasic();
		DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
		GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
		BoardSource boardSource = new BoardSourceBasic();
		BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
		
		Game g = gameFactory.createGame("test1", 3, new GameFlow(phaseFactory), boardManager);
		
		g.addPlayer(Mockito.mock(Player.class));
		
		Mockito.verify(spyBoardStrategy, Mockito.times(1)).getBoard(Matchers.any(), Matchers.any(), Matchers.any());
	}
	
	
	@TestConfiguration
	static class TestConfig {

		@Autowired 
		DiscardPile discard;

		@Autowired 
		PlayerList players;

		@Autowired
		private BoardManagerFactory boardManagerFactory;
		
		@Bean
		@Primary
		GameFactory gameFactoryWithSpyBoard(@Autowired @Qualifier("spyBoardStrategy") BoardStrategy boardStrategyParam) {
			return (name, numberOfPlayers, phases, boardManager) -> createGameWithSpyBoard(name, boardStrategyParam);
		}
		
		@Bean
		@Scope("prototype")
		public Game createGameWithSpyBoard(String gameName, BoardStrategy paramBoardStrategy) {
			CardFactory guildFactory = new GuildCardFactoryBasic();
			DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
			GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
			BoardSource boardSource = new BoardSourceBasic();
			BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
			
			PostTurnActions postTurnActions = new PostTurnActions();

			//TODO: this is done to simplify test setup, but maybe should be moved there or all Game dependencies re-evaluated
			
			Game g = new Game(gameName, 3, new Ages(), postTurnActions,
					new PostTurnActions(), discard, players, new GameFlow(phaseFactory), boardManager);

			//TODO: this was originally in the Game class. Putting it here makes that more flexible in testing situations. Worth it?
			postTurnActions.add(null, g.new PlayCardsAction());
			postTurnActions.add(null, g.new ResolveCommerceAction());
			postTurnActions.add(null, g.new DiscardFinalCardAction());
			postTurnActions.add(null, g.new ResolveConflictAction());

			return g;
		}
								
		@Bean
		@Primary
		BoardStrategy spyBoardStrategy(@Autowired BoardStrategy boardStrategyParam) {
			BoardStrategy spy = Mockito.spy(boardStrategyParam);
			Mockito.doReturn(new Giza(true)).when(spy).getBoard(Matchers.any(), Matchers.any(), Matchers.any());
			return spy;
		}
	}
}

