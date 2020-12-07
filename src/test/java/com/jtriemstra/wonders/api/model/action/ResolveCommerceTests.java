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
import com.jtriemstra.wonders.api.model.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.resource.TradingPayment;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})	
public class ResolveCommerceTests {
	
	@Autowired
	@Qualifier("createGameFactory")
	GameFactory gameFactory;
	
	@Autowired
	@Qualifier("createFinalTurnGameFactory")
	GameFactory finalTurnGameFactory;

	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
	
	@Test
	public void when_not_final_turn_commerce_happens() {
		Game g = gameFactory.createGame("test", boardFactory);
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		
		p1.schedulePayment(new TradingPayment(2, p1, p2));
		
		g.notifyWaiting(Wait.For.TURN);
		
		Assertions.assertEquals(1, p1.getCoins());
		Assertions.assertEquals(5, p2.getCoins());
	}

	@Test
	public void when_final_turn_commerce_happens() {
		Game g = finalTurnGameFactory.createGame("test", boardFactory);
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");

		p1.schedulePayment(new TradingPayment(2, p1, p2));
		
		g.notifyWaiting(Wait.For.TURN);
		
		Assertions.assertEquals(1, p1.getCoins());
		Assertions.assertEquals(5, p2.getCoins());
	}
	
	@TestConfiguration
	static class TestConfig {
		
		@Autowired
		PlayerFactory playerFactory;
		
		@Bean
		@Profile("test")
		@Primary
		public GameFactory createFinalTurnGameFactory() {
			return (name, boardFactory) -> createFinalTurnGame(name, boardFactory);
		}
		
		@Bean
		@Scope("prototype")
		public Game createFinalTurnGame(String gameName, BoardFactory boardFactory) {
			Ages spyAges = Mockito.spy(new Ages());
			Mockito.doReturn(true).when(spyAges).isFinalTurn();
			Mockito.doReturn(1).when(spyAges).getCurrentAge();
			
			PostTurnActions postTurnActions = new PostTurnActions();
			
			Game g = new Game(gameName, boardFactory, spyAges, new DefaultDeckFactory(), postTurnActions, new PostTurnActions());
			
			//TODO: this was originally in the Game class. Putting it here makes that more flexible in testing situations. Worth it?
			postTurnActions.add(null, g.new PlayCardsAction());
			postTurnActions.add(null, g.new ResolveCommerceAction());
			postTurnActions.add(null, g.new DiscardFinalCardAction());
			postTurnActions.add(null, g.new ResolveConflictAction());
			
			return g;
		}
		
	
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
				p.addNextAction(new Wait(Wait.For.TURN));
			}
			
			Mockito.when(mock.allWaiting()).thenReturn(true);
			return mock;
		}
	}
}
