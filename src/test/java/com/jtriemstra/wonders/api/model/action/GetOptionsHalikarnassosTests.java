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
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.StonePit;
import com.jtriemstra.wonders.api.model.card.Tavern;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GetOptionsHalikarnassosTests {
	
	@Autowired
	GameFactory realGameFactory;
	
	@Autowired
	@Qualifier("createMockPlayerFactory")
	PlayerFactory mockPlayerFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;	
	
	@Test
	public void when_discard_is_empty_then_next_action_is_wait() {
		Game g = realGameFactory.createGame("test1", boardFactory);
		Player p1 = mockPlayerFactory.createPlayer("test1");
		g.addPlayer(p1);
		p1.addNextAction(new GetOptionsHalikarnassos());
		
		Assertions.assertEquals(0, g.getDiscardCards().length);
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) p1.doAction(r, g);
		
		Assertions.assertEquals("wait", p1.getNextAction().toString());
		Assertions.assertNull(r1.getCards());
	}
	
	@Test
	public void when_discard_has_cards_and_board_is_empty_then_next_action_is_play() {
		Game g = Mockito.spy(realGameFactory.createGame("test1", boardFactory));
		Mockito.doReturn(new Card[] {new StonePit(3,1), new Tavern(4,2)}).when(g).getDiscardCards();
		
		Player p1 = mockPlayerFactory.createPlayer("test1");
		g.addPlayer(p1);
		p1.addNextAction(new GetOptionsHalikarnassos());
		
		Assertions.assertEquals(2, g.getDiscardCards().length);
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) p1.doAction(r, g);
		
		Assertions.assertEquals("playFree", p1.getNextAction().toString());
		Assertions.assertNotNull(r1.getCards());
		Assertions.assertEquals(2, r1.getCards().size());
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
