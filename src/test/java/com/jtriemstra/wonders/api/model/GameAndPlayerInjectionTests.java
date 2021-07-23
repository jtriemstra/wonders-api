package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

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

import com.jtriemstra.wonders.api.model.GeneralBeanFactory.BoardManagerFactory;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.PossibleActions;
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
import com.jtriemstra.wonders.api.model.phases.Phases;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameAndPlayerInjectionTests {
	
	@Autowired
	GameFactory gameFactory;
		
	@Autowired
	@Qualifier("createSpyPlayerFactory")
	PlayerFactory spyPlayerFactory;

	@Autowired
	@Qualifier("createNamedBoardStrategy")
	private BoardStrategy boardStrategy;

	@Autowired
	@Qualifier("spyBoardStrategy")
	private BoardStrategy spyBoardStrategy;

	@Autowired
	private BoardManagerFactory boardManagerFactory;
	
	@Test
	public void when_using_factory_and_spy_players_then_count_is_correct() {
		CardFactory guildFactory = new GuildCardFactoryBasic();
		DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
		GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
		BoardSource boardSource = new BoardSourceBasic();
		BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
		
		Game g = gameFactory.createGame("test1", 3, new Phases(phaseFactory), boardManager);
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Player p2 = spyPlayerFactory.createPlayer("test2");
		g.addPlayer(p1);
		g.addPlayer(p2);
		
		assertEquals(2, g.getNumberOfPlayers());
		Assertions.assertEquals("Giza", p1.getBoardName());	
	}

	@Test
	public void when_using_spy_board_factory_then_spy_result_used_by_game() {
		CardFactory guildFactory = new GuildCardFactoryBasic();
		DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
		GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
		BoardSource boardSource = new BoardSourceBasic();
		BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
		
		Game g = gameFactory.createGame("test1", 3, new Phases(phaseFactory), boardManager);
		
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Player p2 = spyPlayerFactory.createPlayer("test2");
		g.addPlayer(p1);
		g.addPlayer(p2);
		
		Assertions.assertEquals("Giza", p1.getBoardName());	
	}
	
	@TestConfiguration
	static class TestConfig {

		@Autowired
		@Qualifier("createNamedBoardStrategy")
		private BoardStrategy boardStrategy1;
		
		@Bean
		@Scope("prototype")
		public Player createSpyPlayer(String playerName) {
			ActionList spyList = Mockito.spy(new ActionList());
			PossibleActions mockPossible = Mockito.mock(PossibleActions.class);
			Mockito.when(mockPossible.toString()).thenReturn(playerName.equals("test2") ? "mock actions 2" : "mock actions 1");
			Mockito.doReturn(mockPossible).when(spyList).getNext();
			
			return Mockito.spy(new Player(playerName, spyList, new ArrayList<>(), new ArrayList<>(), new CardList()));
		}
		
		@Bean
		@Primary
		public PlayerFactory createSpyPlayerFactory() {
			return name -> createSpyPlayer(name);
		}

		@Bean
		@Scope("prototype")
		@Primary
		BoardStrategy spyBoardStrategy() {
			BoardStrategy spy = Mockito.spy(boardStrategy1);
			Mockito.doReturn(new Giza(true)).when(spy).getBoard(Matchers.any(), Matchers.any(), Matchers.any());
			return spy;
			//return boardStrategy1;
		}
	}
}
