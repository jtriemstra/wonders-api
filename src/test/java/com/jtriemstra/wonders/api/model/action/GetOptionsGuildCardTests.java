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
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
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
@Import(TestBase.TestConfig.class)
public class GetOptionsGuildCardTests extends TestBase {
	
	@Test
	public void when_neighbors_have_no_guild_cards_then_do_nothing() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
				
		p1.addNextAction(new GetOptionsGuildCard());
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertTrue(r1 instanceof WaitResponse);
		
	}
	
	@Test
	public void when_neighbors_have_guild_cards_then_have_choice() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		fakePreviousCard(g.getPlayer("test2"), new ScientistsGuild(3,3), g);
		fakePreviousCard(g.getPlayer("test2"), new SpiesGuild(3,3), g);
		fakePreviousCard(g.getPlayer("test3"), new TradersGuild(3,3), g);
		
		Mockito.doReturn(true).when(g).isFinalAge();
		Mockito.doReturn(true).when(g).isFinalTurn();
		
		p1.addNextAction(new GetOptionsGuildCard());
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertTrue(r1 instanceof OptionsGuildResponse);
		Assertions.assertEquals("chooseGuild", p1.getNextAction().toString());
	}
	
	@Test
	public void when_not_final_age_and_turn_do_nothing() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		fakePreviousCard(g.getPlayer("test2"), new ScientistsGuild(3,3), g);
		fakePreviousCard(g.getPlayer("test2"), new SpiesGuild(3,3), g);
		fakePreviousCard(g.getPlayer("test3"), new TradersGuild(3,3), g);
		
		Mockito.doReturn(false).when(g).isFinalAge();
		Mockito.doReturn(false).when(g).isFinalTurn();
				
		p1.addNextAction(new GetOptionsGuildCard());
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = p1.doAction(r, g);
		
		Assertions.assertTrue(r1 instanceof WaitResponse);
	}		
}
