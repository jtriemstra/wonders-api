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
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.Babylon;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.StonePit;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GetOptionsBabylonTests {
	
	@Autowired
	GameFactory realGameFactory;
	
	@Autowired
	@Qualifier("createMockPlayerFactory")
	PlayerFactory mockPlayerFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;	
	
	@Test
	public void when_not_final_turn_then_do_nothing() {
		Game g = realGameFactory.createGame("test1", boardFactory);
		Player p1 = mockPlayerFactory.createPlayer("test1");
		g.addPlayer(p1);
		Babylon b = new Babylon(false);
		p1.addNextAction(b.new GetOptionsBabylon());
		
		Assertions.assertFalse(g.isFinalTurn());
		
		OptionsRequest r = new OptionsRequest();
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertEquals("wait", p1.getNextAction().toString());
		Assertions.assertTrue(r1 instanceof WaitResponse);
	}
	
	@Test
	public void when_final_turn_then_return_something() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerFactory.createPlayer("test1");
		g.addPlayer(p1);
		Babylon b = new Babylon(false);
		p1.addNextAction(b.new GetOptionsBabylon());
		Mockito.when(g.isFinalTurn()).thenReturn(true);
		
		Assertions.assertTrue(g.isFinalTurn());
		
		OptionsRequest r = new OptionsRequest();
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertNotEquals("wait", p1.getNextAction().toString());
		Assertions.assertFalse(r1 instanceof WaitResponse);
	}
	
	@Test
	public void when_final_turn_and_have_card_then_can_play() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Player p1 = mockPlayerFactory.createPlayer("test1");
		g.addPlayer(p1);
		Babylon b = new Babylon(false);
		p1.addNextAction(b.new GetOptionsBabylon());
		Mockito.when(g.isFinalTurn()).thenReturn(true);
		p1.receiveCard(new StonePit(3,1));
		
		Assertions.assertTrue(g.isFinalTurn());
		
		OptionsRequest r = new OptionsRequest();
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertEquals("play;discard", p1.getNextAction().toString());
		Assertions.assertFalse(r1 instanceof WaitResponse);
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
		
	}
}
