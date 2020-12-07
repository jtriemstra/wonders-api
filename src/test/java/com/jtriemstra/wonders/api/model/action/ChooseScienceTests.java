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

import com.jtriemstra.wonders.api.dto.request.ChooseScienceRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.ScienceType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class ChooseScienceTests {
	
	@Autowired
	GameFactory realGameFactory;
	
	@Autowired
	@Qualifier("createMockPlayerFactory")
	PlayerFactory mockPlayerFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;	
	
	@Test
	public void when_choosing_science_then_added_to_player() {
		Game g = realGameFactory.createGame("test1", boardFactory);
		Player p1 = mockPlayerFactory.createPlayer("test1");
		g.addPlayer(p1);
		ChooseScience cs = new ChooseScience();
		p1.addNextAction(cs);
		
		Assertions.assertFalse(g.isFinalTurn());
		
		ChooseScienceRequest r = new ChooseScienceRequest();
		r.setChoice(ScienceType.TABLET);
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertEquals("wait", p1.getNextAction().toString());
		Assertions.assertTrue(r1 instanceof ActionResponse);
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
