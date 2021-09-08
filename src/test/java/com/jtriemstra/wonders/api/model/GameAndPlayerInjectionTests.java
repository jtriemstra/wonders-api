package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

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

import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.PossibleActions;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.Giza;
import com.jtriemstra.wonders.api.notifications.NotificationService;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameAndPlayerInjectionTests {
	
	@Autowired
	@Qualifier("basicGame")
	Game basicGame;
	
	@Autowired
	@Qualifier("createSpyPlayerFactory")
	PlayerFactory spyPlayerFactory;
		
	@Test
	public void when_using_factory_and_spy_players_then_count_is_correct() {
		
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Player p2 = spyPlayerFactory.createPlayer("test2");
		basicGame.addPlayer(p1);
		basicGame.addPlayer(p2);
		
		assertEquals(2, basicGame.getNumberOfPlayers());
		Assertions.assertEquals("Giza", p1.getBoardName());	
	}
	
	@Test
	public void with_multiple_tests_count_is_still_correct() {
		
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Player p2 = spyPlayerFactory.createPlayer("test2");
		basicGame.addPlayer(p1);
		basicGame.addPlayer(p2);
		
		assertEquals(2, basicGame.getNumberOfPlayers());
	}
		
	@TestConfiguration
	public static class TestConfig {

		@Autowired
		GameFactory gameFactory;
								
		@Bean
		@Scope("prototype")
		public Player createSpyPlayer(String playerName) {
			ActionList spyList = Mockito.spy(new ActionList());
			PossibleActions mockPossible = Mockito.mock(PossibleActions.class);
			Mockito.when(mockPossible.toString()).thenReturn(playerName.equals("test2") ? "mock actions 2" : "mock actions 1");
			Mockito.doReturn(mockPossible).when(spyList).getNext();
			
			return Mockito.spy(new Player(playerName, spyList, new ArrayList<>(), new ArrayList<>(), new CardList(), new NotificationService()));
		}
		
		@Bean
		@Primary
		public PlayerFactory createSpyPlayerFactory() {
			return name -> createSpyPlayer(name);
		}

		@Bean
		@Scope("prototype")
		@Primary
		BoardStrategy spyBoardStrategy(@Autowired @Qualifier("createNamedBoardStrategy") BoardStrategy createNamedBoardStrategy) {
			BoardStrategy spy = Mockito.spy(createNamedBoardStrategy);
			Mockito.doReturn(new Giza(true)).when(spy).getBoard(Mockito.any(), Mockito.any(), Mockito.any());
			return spy;
		}

		@Bean
		public Game basicGame() {
			return gameFactory.createGame("test2", 3, false, BoardSide.A_OR_B, false);
		}
	}
}
