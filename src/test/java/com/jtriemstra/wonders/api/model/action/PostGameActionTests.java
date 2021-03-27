package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.Ages;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.GameNotifyWaitingTrueTests.DummyPostGameAction1;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.board.Ephesus;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class PostGameActionTests {

	@Autowired
	PlayerFactory playerFactory;

	@Autowired
	@Qualifier("createFinalAgeGameFactory")
	GameFactory finalAgeGameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
	
	@Test
	public void when_finishing_game_choose_science_option_works() {
		Game g = finalAgeGameFactory.createGame("test", boardFactory);
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		g.addPostGameAction(p1, new GetOptionsScience());
		
		Assertions.assertEquals("wait", p1.getNextAction().toString());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsScience);
	}

	@TestConfiguration
	static class TestConfig {
		
		@Autowired
		PlayerFactory playerFactory;
		
		@Autowired
		PlayerList realPlayerList;
		
		@Bean
		@Primary
		@Scope("prototype")
		PlayerList createMockPlayerList() {
			PlayerList mock = Mockito.spy(realPlayerList);
			mock.addPlayer(playerFactory.createPlayer("test1"));
			mock.addPlayer(playerFactory.createPlayer("test2"));
			mock.addPlayer(playerFactory.createPlayer("test3"));
			
			for (Player p : mock) {
				p.addNextAction(new WaitTurn());
			}
			
			Mockito.when(mock.allWaiting()).thenReturn(true);
			return mock;
		}
		
		@Bean
		@Profile("test")
		@Primary
		public GameFactory createFinalAgeGameFactory() {
			return (name, boardFactory) -> createFinalAgeGame(name, boardFactory);
		}
		
		@Bean
		@Scope("prototype")
		public Game createFinalAgeGame(String gameName, BoardFactory boardFactory) {
			Ages spyAges = Mockito.spy(new Ages());
			Mockito.doReturn(true).when(spyAges).isFinalAge();
			Mockito.doReturn(true).when(spyAges).isFinalTurn();
			return new Game(gameName, boardFactory, spyAges, new DefaultDeckFactory(), new PostTurnActions(), new PostTurnActions());
		}
		
	}
}
