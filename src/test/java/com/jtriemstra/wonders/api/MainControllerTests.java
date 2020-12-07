package com.jtriemstra.wonders.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

//@WebMvcTest(MainController.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(Alphanumeric.class)
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@ActiveProfiles("test")
public class MainControllerTests {
	@Autowired
	private MockMvc mockMvc;
	
	/*@MockBean
	private GameFactory gameFactory;
	
	@MockBean
	private PlayerFactory playerFactory;*/
	
	@Test
	public void a001_when_call_create_response_is_success() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/create?playerName=test1"))
			.andDo(print())
			.andExpect(status().isOk())
            .andReturn();
		
		//TODO : check response content, currently getting error on the string representation of nextActions back to a real implementation
		/*String content = result.getResponse().getContentAsString();
		
		Assertions.assertNotNull(response.getBoardName());
		Assertions.assertNotEquals("", response.getBoardName());*/
	}
	
	@Test
	public void a002_when_call_join_response_is_success() throws Exception {
		/*this.mockMvc.perform(get("/create?playerName=test1"))
		.andDo(print())
		.andExpect(status().isOk());*/
		
		this.mockMvc.perform(get("/join?playerName=test2&gameName=test1"))
			.andDo(print())
			.andExpect(status().isOk());
	}
	
	//TODO: add board
	
	@Test
	public void a003_when_call_options_response_is_success() throws Exception {
		/*this.mockMvc.perform(get("/create?playerName=test1"))
		.andDo(print())
		.andExpect(status().isOk());*/
		
		/*this.mockMvc.perform(get("/join?playerName=test2&gameName=test1"))
			.andDo(print())
			.andExpect(status().isOk());*/
		
		this.mockMvc.perform(get("/updateGame?playerId=test1&gameName=test1&numberOfPlayers=3"))
			.andDo(print())
			.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/join?playerName=test3&gameName=test1"))
			.andDo(print())
			.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/wait?playerId=test1&gameName=test1"))
			.andDo(print())
			.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/wait?playerId=test2&gameName=test1"))
			.andDo(print())
			.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/wait?playerId=test3&gameName=test1"))
			.andDo(print())
			.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/start?playerId=test1&gameName=test1"))
			.andDo(print())
			.andExpect(status().isOk());
		
		this.mockMvc.perform(get("/options?playerId=test1&gameName=test1"))
			.andDo(print())
			.andExpect(status().isOk());
	}
}
