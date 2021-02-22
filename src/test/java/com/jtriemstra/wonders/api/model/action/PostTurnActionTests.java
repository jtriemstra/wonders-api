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

import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.model.Ages;
import com.jtriemstra.wonders.api.model.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.board.Babylon;
import com.jtriemstra.wonders.api.model.board.Babylon.GetOptionsBabylon;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class PostTurnActionTests {

	@Autowired
	PlayerFactory playerFactory;

	@Autowired
	@Qualifier("createNormalTurnGameFactory")
	GameFactory normalTurnGameFactory;

	@Autowired
	@Qualifier("createFinalTurnGameFactory")
	GameFactory finalTurnGameFactory;

	@Autowired
	@Qualifier("createFinalAgeTurnGameFactory")
	GameFactory finalAgeTurnGameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
	
	@Test
	public void when_finishing_turn_with_no_actions_all_options_for_next_turn() {
		Game g = normalTurnGameFactory.createGame("test", boardFactory);
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("options", p2.getNextAction().toString());
		Assertions.assertEquals("options", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptions);
		Assertions.assertTrue(p2.getNextAction().getByName("options") instanceof GetOptions);
		Assertions.assertTrue(p3.getNextAction().getByName("options") instanceof GetOptions);
	}

	@Test
	public void when_finishing_turn_with_default_actions_all_options_for_next_turn() {
		Game g = normalTurnGameFactory.createGame("test", boardFactory);
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("options", p2.getNextAction().toString());
		Assertions.assertEquals("options", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptions);
		Assertions.assertTrue(p2.getNextAction().getByName("options") instanceof GetOptions);
		Assertions.assertTrue(p3.getNextAction().getByName("options") instanceof GetOptions);
	}

	@Test
	public void when_finishing_turn_with_defaults_and_hali_action_wait_for_hali_options() {
		Game g = normalTurnGameFactory.createGame("test", boardFactory);
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(p1, new GetOptionsHalikarnassos());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsHalikarnassos);
	}

	@Test
	public void when_finishing_turn_with_only_hali_action_wait_for_hali_options() {
		Game g = normalTurnGameFactory.createGame("test", boardFactory);
		
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(p1, new GetOptionsHalikarnassos());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsHalikarnassos);
	}

	@Test
	public void when_finishing_final_turn_with_defaults_and_hali_action_wait_for_hali_options() {
		Game g = finalTurnGameFactory.createGame("test", boardFactory);
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(p1, new GetOptionsHalikarnassos());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsHalikarnassos);
	}

	@Test
	public void when_finishing_final_age_turn_with_defaults_and_hali_action_wait_for_hali_options() {
		Game g = finalAgeTurnGameFactory.createGame("test", boardFactory);
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(p1, new GetOptionsHalikarnassos());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsHalikarnassos);
	}
	
	@Test
	public void when_finishing_final_turn_with_defaults_and_babylon_action_wait_for_babylon_options() {
		Game g = finalTurnGameFactory.createGame("test", boardFactory);
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		Player p1 = g.getPlayer("test1");
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		Babylon b = new Babylon(false);
		g.addPostTurnAction(p1, b.new GetOptionsBabylon());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsBabylon);
		
		
	}

	@Test
	public void when_finishing_normal_turn_with_defaults_and_babylon_action_move_to_next_turn_options() {
		Game g = normalTurnGameFactory.createGame("test", boardFactory);
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		Player p1 = Mockito.spy(g.getPlayer("test1"));
		Player p2 = Mockito.spy(g.getPlayer("test2"));
		Player p3 = Mockito.spy(g.getPlayer("test3"));
		
		Babylon b = new Babylon(false);
		g.addPostTurnAction(p1, b.new GetOptionsBabylon());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsBabylon);
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
		p1.getNextAction().getByName("options").execute(new OptionsRequest(), p1, g);
		
		Assertions.assertEquals("wait", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
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
		public GameFactory createNormalTurnGameFactory() {
			return (name, boardFactory) -> createNormalTurnGame(name, boardFactory);
		}
		
		@Bean
		@Scope("prototype")
		public Game createNormalTurnGame(String gameName, BoardFactory boardFactory) {
			Ages spyAges = Mockito.spy(new Ages());
			return new Game(gameName, boardFactory, spyAges, new DefaultDeckFactory(), new PostTurnActions(), new PostTurnActions());
		}

		@Bean
		@Profile("test")
		public GameFactory createFinalTurnGameFactory() {
			return (name, boardFactory) -> createFinalTurnGame(name, boardFactory);
		}
		
		@Bean
		@Scope("prototype")
		public Game createFinalTurnGame(String gameName, BoardFactory boardFactory) {
			Ages spyAges = Mockito.spy(new Ages());
			Mockito.doReturn(true).when(spyAges).isFinalTurn();
			return new Game(gameName, boardFactory, spyAges, new DefaultDeckFactory(), new PostTurnActions(), new PostTurnActions());
		}

		@Bean
		@Profile("test")
		public GameFactory createFinalAgeTurnGameFactory() {
			return (name, boardFactory) -> createFinalAgeTurnGame(name, boardFactory);
		}
		
		@Bean
		@Scope("prototype")
		public Game createFinalAgeTurnGame(String gameName, BoardFactory boardFactory) {
			Ages spyAges = Mockito.spy(new Ages());
			Mockito.doReturn(true).when(spyAges).isFinalTurn();
			Mockito.doReturn(true).when(spyAges).isFinalAge();
			return new Game(gameName, boardFactory, spyAges, new DefaultDeckFactory(), new PostTurnActions(), new PostTurnActions());
		}
	}
}
