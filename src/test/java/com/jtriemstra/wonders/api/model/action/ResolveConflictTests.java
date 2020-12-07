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
import com.jtriemstra.wonders.api.model.Game.DiscardFinalCardAction;
import com.jtriemstra.wonders.api.model.Game.PlayCardsAction;
import com.jtriemstra.wonders.api.model.Game.ResolveCommerceAction;
import com.jtriemstra.wonders.api.model.Game.ResolveConflictAction;
import com.jtriemstra.wonders.api.model.board.BoardFactory;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class ResolveConflictTests {
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
	public void when_not_final_turn_no_resolution() {
		Game g = gameFactory.createGame("test", boardFactory);
		Player p1 = g.getPlayer("test1");
		p1.addShields(1);
		
		Assertions.assertEquals(0, p1.getVictories().size());
		Assertions.assertEquals(0,  p1.getNumberOfVictories(1));
		
		g.notifyWaiting(Wait.For.TURN);
		
		Assertions.assertEquals(0, p1.getVictories().size());
		Assertions.assertEquals(0,  p1.getNumberOfVictories(1));
		
	}

	@Test
	public void when_final_turn_resolution_is_correct() {
		Game g = finalTurnGameFactory.createGame("test", boardFactory);
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		p1.addShields(1);
		
		Assertions.assertEquals(0, p1.getVictories().size());
		Assertions.assertEquals(0,  p1.getNumberOfVictories(1));
		
		g.notifyWaiting(Wait.For.TURN);
		
		Assertions.assertNotNull(p1.getVictories());
		Assertions.assertEquals(2,  p1.getNumberOfVictories(1));
		Assertions.assertEquals(0,  p2.getNumberOfVictories(1));
		Assertions.assertEquals(0,  p3.getNumberOfVictories(1));
		Assertions.assertEquals(0, p1.getNumberOfDefeats());
		Assertions.assertEquals(1, p2.getNumberOfDefeats());
		Assertions.assertEquals(1, p3.getNumberOfDefeats());
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
			mock.clear();
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
