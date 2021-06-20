package com.jtriemstra.wonders.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jtriemstra.wonders.api.model.Ages;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
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

//@WebMvcTest(MainController.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(Alphanumeric.class)
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class MainControllerInjectionTests {
	
	@Autowired
	private MockMvc mockMvc;	
	
	@Test
	public void a001_when_call_create_response_is_success() throws Exception {
		
		MvcResult result = this.mockMvc.perform(get("/create?playerName=test1"))
				.andDo(print())
				.andExpect(status().isOk())
	            .andReturn();

		
		  this.mockMvc.perform(get("/updateGame?playerId=test1&gameName=test1&numberOfPlayers=3"))
		  	.andDo(print()) 
		  	.andExpect(status().isOk());
		  
			
		  this.mockMvc.perform(get("/join?playerName=test2&gameName=test1"))
			  .andDo(print()) .andExpect(status().isOk());
			  
		  this.mockMvc.perform(get("/join?playerName=test3&gameName=test1"))
			  .andDo(print()) .andExpect(status().isOk());
			  
		
		/*
		 * this.mockMvc.perform(get("/wait?playerId=test1&gameName=test1"))
		 * .andDo(print()) .andExpect(status().isOk());
		 * 
		 * this.mockMvc.perform(get("/wait?playerId=test2&gameName=test1"))
		 * .andDo(print()) .andExpect(status().isOk());
		 * 
		 * this.mockMvc.perform(get("/wait?playerId=test3&gameName=test1"))
		 * .andDo(print()) .andExpect(status().isOk());
		 * 
		 * this.mockMvc.perform(get("/start?playerId=test1&gameName=test1"))
		 * .andDo(print()) .andExpect(status().isOk());
		 * 
		 * MvcResult result1 =
		 * this.mockMvc.perform(get("/options?playerId=test1&gameName=test1"))
		 * .andDo(print()) .andExpect(status().isOk()) .andReturn();
		 * 
		 * MvcResult result2 =
		 * this.mockMvc.perform(get("/options?playerId=test2&gameName=test1"))
		 * .andDo(print()) .andExpect(status().isOk()) .andReturn();
		 * 
		 * MvcResult result3 =
		 * this.mockMvc.perform(get("/options?playerId=test3&gameName=test1"))
		 * .andDo(print()) .andExpect(status().isOk()) .andReturn();
		 * 
		 * String s1 = result1.getResponse().getContentAsString(); String s2 =
		 * result2.getResponse().getContentAsString(); String s3 =
		 * result3.getResponse().getContentAsString();
		 * 
		 * Assertions.assertTrue(s1.contains("Stone Pit"));
		 * Assertions.assertTrue(s2.contains("Altar"));
		 * Assertions.assertTrue(s3.contains("Theater"));
		 */
	}
	
	@TestConfiguration
	static class TestConfig {
		
		public AgeDeck deck;
		
		@Autowired
		PlayerFactory playerFactory;
		
		@Autowired 
		BoardStrategy boardStrategy;
		
		@Bean
		@Scope("prototype")
		@Primary
		public GameFactory createFixedDeckGameFactory() {
			return (name) -> createFixedDeckGame(name);
		}
		
		@Bean
		@Scope("prototype")
		@Primary
		public Game createFixedDeckGame(String gameName) {
			Card[] fixedCards = new Card[] {			
					new StonePit(3, 1), //1
					new Altar(3, 1),
					new Theater(3, 1),
					new Baths(3, 1), //1
					new LumberYard(3, 1),
					new OreVein(3, 1),
					new ClayPool(3, 1), //1
					new ClayPit(3, 1),
					new TimberYard(3, 1),
					new Press(3, 1), //1
					new Glassworks(3, 1),
					new Loom(3, 1),
					new Stockade(3, 1), //1
					new Barracks(3, 1),
					new GuardTower(3, 1),
					new Apothecary(3, 1), //1
					new Scriptorium(3, 1),
					new Workshop(3, 1),
					new WestTradingPost(3, 1), //1
					new EastTradingPost(3, 1),
					new Marketplace(3, 1)
			};
			AgeDeck mockDeck = Mockito.mock(AgeDeck.class);
			
			when(mockDeck.draw()).thenAnswer(new Answer() {
			    //protected int count = players * 7 * (age - 1);
				protected int count = 0;
				
			    public Object answer(InvocationOnMock invocation) {
			    	Card[] cardsForAge = fixedCards;
			    	Card result = cardsForAge[count];
			        count++;
			        return result;
			    }
			});
			
			DeckFactory mockDeckFactory = Mockito.mock(DefaultDeckFactory.class);
			when(mockDeckFactory.getDeck(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockDeck);
			
			Game g = new Game(gameName, boardStrategy, new Ages(), mockDeckFactory, new PostTurnActions(), new PostTurnActions());
						
			return g;
		}
	}
}
