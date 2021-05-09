package com.jtriemstra.wonders.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameList;
import com.jtriemstra.wonders.api.model.Player;

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
