package com.jtriemstra.wonders.api.model;

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

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.Wait;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.board.BoardFactory;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GameNotifyWaitingTrueTests {
	
	@Autowired
	@Qualifier("createGameFactory")
	GameFactory gameFactory;

	@Autowired
	@Qualifier("createFinalAgeGameFactory")
	GameFactory finalAgeGameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
	
	String dummyResults = "";
	
	@Test
	public void when_notify_waiting_for_turn_return_false() {
		Game g = gameFactory.createGame("test", boardFactory);
		
		//Assertions.assertEquals(false, g.notifyWaiting(Wait.For.TURN, new WaitTurn()));
	}
	
	@Test
	public void when_notify_waiting_for_turn_execute_actions_in_order() {
		dummyResults = "";
		Game g = gameFactory.createGame("test", boardFactory);
		g.addPostTurnAction(null, new DummyPostTurnAction2());
		g.addPostTurnAction(null, new DummyPostTurnAction1());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, null, g);
		
		Assertions.assertEquals("12", dummyResults);
	}
	
	@Test
	public void when_notify_waiting_for_turn_execute_postgame_actions_in_order() {
		dummyResults = "";
		Game g = finalAgeGameFactory.createGame("test", boardFactory);
		g.addPostGameAction(null, new DummyPostGameAction1());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, null, g);
		
		Assertions.assertEquals("game1", dummyResults);
	}
	
	public class DummyPostTurnAction1 implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {			
			return 1;
		}
		
		@Override
		public String getName() {
			return "dummy1";
		}

		@Override
		public ActionResponse execute(Game game) {
			dummyResults += "1";
			return null;
		}
		
	}
	
	public class DummyPostTurnAction2 implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {			
			return 2;
		}
		
		@Override
		public String getName() {
			return "dummy2";
		}

		@Override
		public ActionResponse execute(Game game) {
			dummyResults += "2";
			return null;
		}
		
	}
	
	public class DummyPostGameAction1 implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {			
			return 1;
		}
		
		@Override
		public String getName() {
			return "dummyGame1";
		}

		@Override
		public ActionResponse execute(Game game) {
			dummyResults += "game1";
			return null;
		}
		
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
