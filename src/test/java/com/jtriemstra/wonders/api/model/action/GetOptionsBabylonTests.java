package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
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
@Import(TestBase.TestConfig.class)
public class GetOptionsBabylonTests extends TestBase {
	
	@Test
	public void when_not_final_turn_then_do_nothing() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		
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
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayer(g);
		Mockito.doReturn(true).when(g).isFinalTurn();
		
		Babylon b = new Babylon(false);
		p1.addNextAction(b.new GetOptionsBabylon());
		
		Assertions.assertTrue(g.isFinalTurn());
		
		OptionsRequest r = new OptionsRequest();
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertNotEquals("wait", p1.getNextAction().toString());
		Assertions.assertFalse(r1 instanceof WaitResponse);
	}
	
	@Test
	public void when_final_turn_and_have_card_then_can_play() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayerWithCard(new StonePit(3,1), g);
		Mockito.doReturn(true).when(g).isFinalTurn();
		
		Babylon b = new Babylon(false);
		p1.addNextAction(b.new GetOptionsBabylon());
				
		Assertions.assertTrue(g.isFinalTurn());
		
		OptionsRequest r = new OptionsRequest();
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertEquals("play;discard", p1.getNextAction().toString());
		Assertions.assertFalse(r1 instanceof WaitResponse);
	}
}
