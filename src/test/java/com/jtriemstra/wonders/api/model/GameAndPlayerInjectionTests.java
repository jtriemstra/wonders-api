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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.PossibleActions;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.board.Giza;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GameAndPlayerInjectionTests {
	
	@Autowired
	GameFactory gameFactory;
		
	@Autowired
	@Qualifier("createSpyPlayerFactory")
	PlayerFactory spyPlayerFactory;

	@Autowired
	@Qualifier("createNamedBoardFactory")
	private BoardFactory boardFactory;

	@Autowired
	@Qualifier("spyBoardFactory")
	private BoardFactory spyBoardFactory;
	
	@Test
	public void when_using_factory_and_spy_players_then_count_is_correct() {
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Player p2 = spyPlayerFactory.createPlayer("test2");
		g.addPlayer(p1);
		g.addPlayer(p2);
		
		assertEquals(2, g.getNumberOfPlayers());
	}

	@Test
	public void when_using_spy_board_factory_then_spy_result_used_by_game() {
		Game g = gameFactory.createGame("test1", spyBoardFactory);
		
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Player p2 = spyPlayerFactory.createPlayer("test2");
		g.addPlayer(p1);
		g.addPlayer(p2);
		
		Assertions.assertEquals("Giza", p1.getBoardName());	
	}
	
	@TestConfiguration
	static class TestConfig {

		@Autowired
		@Qualifier("createNamedBoardFactory")
		private BoardFactory boardFactory;
		
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
		BoardFactory spyBoardFactory() {
			BoardFactory spy = Mockito.spy(boardFactory);
			Mockito.doReturn(new Giza(true)).when(spy).getBoard();
			return spy;
		}
	}
}
