package com.jtriemstra.wonders.api.model.action;

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
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsGuildResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.ScientistsGuild;
import com.jtriemstra.wonders.api.model.card.SpiesGuild;
import com.jtriemstra.wonders.api.model.card.TradersGuild;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GetOptionsGuildCardTests {
	
	@Autowired
	GameFactory realGameFactory;
	
	@Autowired
	@Qualifier("createMockPlayerFactory")
	PlayerFactory mockPlayerFactory;

	@Autowired
	@Qualifier("createPlayerWithCardFactory")
	PlayerFactory mockPlayerWithCardFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;	
	
	@Test
	public void when_neighbors_have_no_guild_cards_then_do_nothing() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerFactory.createPlayer("test1");
		Player p2 = mockPlayerFactory.createPlayer("test2");
		Player p3 = mockPlayerFactory.createPlayer("test3");
		
		Mockito.doReturn(p3).when(g).getLeftOf(p1);
		Mockito.doReturn(p2).when(g).getRightOf(p1);
		
		g.addPlayer(p1);
		
		p1.addNextAction(new GetOptionsGuildCard());
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertTrue(r1 instanceof WaitResponse);
		
	}
	
	@Test
	public void when_neighbors_have_guild_cards_then_have_choice() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerWithCardFactory.createPlayer("test1");
		Player p2 = mockPlayerWithCardFactory.createPlayer("test2");
		Player p3 = mockPlayerWithCardFactory.createPlayer("test3");
		
		Mockito.doReturn(true).when(g).isFinalAge();
		Mockito.doReturn(true).when(g).isFinalTurn();
		Mockito.doReturn(p3).when(g).getLeftOf(p1);
		Mockito.doReturn(p2).when(g).getRightOf(p1);
		
		g.addPlayer(p1);
		
		p1.addNextAction(new GetOptionsGuildCard());
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertTrue(r1 instanceof OptionsGuildResponse);
		Assertions.assertEquals("chooseGuild", p1.getNextAction().toString());
	}
	
	@Test
	public void when_not_final_age_and_turn_do_nothing() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerWithCardFactory.createPlayer("test1");
		Player p2 = mockPlayerWithCardFactory.createPlayer("test2");
		Player p3 = mockPlayerWithCardFactory.createPlayer("test3");
		
		Mockito.doReturn(false).when(g).isFinalAge();
		Mockito.doReturn(false).when(g).isFinalTurn();
		Mockito.doReturn(p3).when(g).getLeftOf(p1);
		Mockito.doReturn(p2).when(g).getRightOf(p1);
		
		g.addPlayer(p1);
		
		p1.addNextAction(new GetOptionsGuildCard());
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertTrue(r1 instanceof WaitResponse);
	}
		
	@TestConfiguration
	static class TestConfig {
		
		@Autowired
		PlayerFactory realPlayerFactory;
		
		@Bean
		@Profile("test")
		@Primary
		public PlayerFactory createMockPlayerFactory() {
			return (name) -> createMockPlayer(name);
		}
		
		@Bean
		@Scope("prototype")
		public Player createMockPlayer(String name) {
			Player p = Mockito.spy(realPlayerFactory.createPlayer(name));
			Mockito.doReturn(ResourceType.STONE).when(p).getBoardResourceName();
			return p;
		}
		
		@Bean
		@Profile("test")
		public PlayerFactory createPlayerWithCardFactory() {
			return (name) -> createPlayerWithCard(name);
		}
		
		@Bean
		@Scope("prototype")
		public Player createPlayerWithCard(String name) {
			CardList cl1 = new CardList();
			cl1.add(new ScientistsGuild(3,3));
			cl1.add(new SpiesGuild(3,3));
			CardList cl2 = new CardList();
			cl2.add(new TradersGuild(3,3));
			
			
			Player p;
			if (name.equals("test2")) {
				p = new Player(name, new ActionList(), new ArrayList<>(), new ArrayList<>(), cl1);
			}
			else if (name.equals("test3")) {
				p = new Player(name, new ActionList(), new ArrayList<>(), new ArrayList<>(), cl2);
			}
			else {
				p = new Player(name, new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList());
			}
			Player p1 = Mockito.spy(p);
			Mockito.doReturn(ResourceType.STONE).when(p1).getBoardResourceName();
			return p1;
		}
		
	}
}
