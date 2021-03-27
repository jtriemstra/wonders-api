package com.jtriemstra.wonders.api;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.model.Ages;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.GameList;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.Altar;
import com.jtriemstra.wonders.api.model.card.Apothecary;
import com.jtriemstra.wonders.api.model.card.Barracks;
import com.jtriemstra.wonders.api.model.card.Baths;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.ClayPit;
import com.jtriemstra.wonders.api.model.card.ClayPool;
import com.jtriemstra.wonders.api.model.card.EastTradingPost;
import com.jtriemstra.wonders.api.model.card.Glassworks;
import com.jtriemstra.wonders.api.model.card.GuardTower;
import com.jtriemstra.wonders.api.model.card.Loom;
import com.jtriemstra.wonders.api.model.card.LumberYard;
import com.jtriemstra.wonders.api.model.card.Marketplace;
import com.jtriemstra.wonders.api.model.card.OreVein;
import com.jtriemstra.wonders.api.model.card.Press;
import com.jtriemstra.wonders.api.model.card.Scriptorium;
import com.jtriemstra.wonders.api.model.card.Stockade;
import com.jtriemstra.wonders.api.model.card.StonePit;
import com.jtriemstra.wonders.api.model.card.Theater;
import com.jtriemstra.wonders.api.model.card.TimberYard;
import com.jtriemstra.wonders.api.model.card.WestTradingPost;
import com.jtriemstra.wonders.api.model.card.Workshop;
import com.jtriemstra.wonders.api.model.deck.AgeDeck;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(MainController.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@ActiveProfiles("test")
public class MainControllerChooseGuildTests {
	
	@Autowired
	private MockMvc mockMvc;	
	
	static Player p;
	
	@Test
	public void when_call_choose_guild_decode_works() throws Exception {
		
		MvcResult result = this.mockMvc.perform(get("/chooseGuild?gameName=test1&playerId=test1&optionName=Magistrates%2520Guild"))
				.andDo(print())
				.andExpect(status().isOk())
	            .andReturn();
		
		Mockito.verify(p, Mockito.times(1)).doAction(Mockito.any(ActionRequest.class), Mockito.any(Game.class));
	}
	
	@TestConfiguration
	static class TestConfig {
		
		@Bean
		@Primary
		public GameList createMockGameList() {
			p = Mockito.mock(Player.class);
			Game game = Mockito.mock(Game.class);
			Mockito.when(game.getPlayer(Mockito.anyString())).thenReturn(p);
			GameList g = Mockito.mock(GameList.class);
			Mockito.when(g.get(Mockito.anyString())).thenReturn(game);
			
			return g;
		}
		
		
	}
}
