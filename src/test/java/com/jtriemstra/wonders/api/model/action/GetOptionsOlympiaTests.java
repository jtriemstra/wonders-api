package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.HashSet;

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
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.StonePit;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GetOptionsOlympiaTests {
	
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
	public void when_havent_used_can_play_expensive_card_for_free() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerFactory.createPlayer("test1");
		Mockito.doReturn(1).when(g).getCurrentAge();
		
		g.addPlayer(p1);
		p1.addNextAction(new GetOptionsOlympia(new HashSet<>()));
		p1.receiveCard(new Palace(3,3));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("discard;playFree", p1.getNextAction().toString());
		
	}
	
	//TODO: maybe nice to change this so if "play" is an option "playFree" is hidden. Should probably take into account trading costs as well - allow both if you'd have to pay.
	@Test
	public void when_havent_used_can_play_cheap_card_for_free() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerFactory.createPlayer("test1");
		Mockito.doReturn(1).when(g).getCurrentAge();
		
		g.addPlayer(p1);
		p1.addNextAction(new GetOptionsOlympia(new HashSet<>()));
		p1.receiveCard(new StonePit(3,1));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("play;discard;playFree", p1.getNextAction().toString());		
	}
	
	@Test
	public void when_used_cannnot_play_for_free() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerFactory.createPlayer("test1");
		Mockito.doReturn(1).when(g).getCurrentAge();
		
		g.addPlayer(p1);
		HashSet<Integer> h = new HashSet<>();
		h.add(1);
		p1.addNextAction(new GetOptionsOlympia(h));
		p1.receiveCard(new Palace(3,3));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("discard", p1.getNextAction().toString());		
	}
	

	@Test
	public void when_used_in_one_age_can_use_later() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerFactory.createPlayer("test1");
		Mockito.doReturn(2).when(g).getCurrentAge();
		
		g.addPlayer(p1);
		HashSet<Integer> h = new HashSet<>();
		h.add(1);
		p1.addNextAction(new GetOptionsOlympia(h));
		p1.receiveCard(new Palace(3,3));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("discard;playFree", p1.getNextAction().toString());		
	}
	

	@Test
	public void when_card_already_played_cannot_play_again() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerWithCardFactory.createPlayer("test1");
		Mockito.doReturn(2).when(g).getCurrentAge();
		
		g.addPlayer(p1);
		HashSet<Integer> h = new HashSet<>();
		h.add(1);
		p1.addNextAction(new GetOptionsOlympia(h));
		p1.receiveCard(new Palace(3,3));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("discard", p1.getNextAction().toString());		
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
			Player p = realPlayerFactory.createPlayer(name);
			
			return Mockito.spy(p);
		}
		
		@Bean
		@Profile("test")
		public PlayerFactory createPlayerWithCardFactory() {
			return (name) -> createPlayerWithCard(name);
		}
		
		@Bean
		@Scope("prototype")
		public Player createPlayerWithCard(String name) {
			CardList cl = new CardList();
			cl.add(new Palace(3,3));
			Player p = new Player(name, new ActionList(), new ArrayList<>(), new ArrayList<>(), cl);
			
			return Mockito.spy(p);
		}
		
	}
}
