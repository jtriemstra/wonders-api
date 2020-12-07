package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.PossibleActions;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class MultipleMockActionList2InjectionTests {
	
	@Autowired
	@Qualifier("createSpyPlayerFactory")
	PlayerFactory spyPlayerFactory;
	
	@Test
	public void when_using_spy_factory_then_can_mock_actions_independently() {
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Player p2 = spyPlayerFactory.createPlayer("test2");
		assertEquals("mock actions 1", p1.getNextAction().toString());
		assertEquals("mock actions 2", p2.getNextAction().toString());
	}
		
	
	@TestConfiguration
	static class TestConfig {
		
		@Bean
		@Scope("prototype")
		public Player createSpyPlayer(String playerName) {
			ActionList spyList = Mockito.spy(new ActionList());
			PossibleActions mockPossible = Mockito.mock(PossibleActions.class);
			Mockito.when(mockPossible.toString()).thenReturn(playerName.equals("test2") ? "mock actions 2" : "mock actions 1");
			Mockito.doReturn(mockPossible).when(spyList).getNext();
			
			return Mockito.spy(new Player(playerName, spyList, new ArrayList<>(), new ArrayList<>(), new CardList()));
		}
		
		@Bean
		@Primary
		public PlayerFactory createSpyPlayerFactory() {
			return name -> createSpyPlayer(name);
		}
		
	}
}
