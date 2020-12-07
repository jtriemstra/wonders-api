package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.action.PossibleActions;
import com.jtriemstra.wonders.api.model.deprecated.ActionList;
import com.jtriemstra.wonders.api.model.deprecated.ActionListFactory;
import com.jtriemstra.wonders.api.model.deprecated.Player;
import com.jtriemstra.wonders.api.model.deprecated.PlayerFactory;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class MockActionListInjectionTests {
	@Autowired
	PlayerFactory playerFactory;
	
	@Test
	public void when_spy_action_list_exists_then_it_is_injected() {
		Player p = playerFactory.createPlayer("player-name");
		assertNotNull(p.getNextAction());
		assertEquals("", p.getNextAction().toString());
	}
	
	@TestConfiguration
	static class TestConfig {
		
		@Bean
		@Scope("prototype")
		@Primary
		public ActionList createSpyActionList() {
			ActionList spy = Mockito.spy(ActionList.class);
			Mockito.doReturn(new PossibleActions()).when(spy).getNext();
			return spy;
		}
		
		@Bean
		public ActionListFactory createSpyActionListFactory() {
			return () -> createSpyActionList();
		}
	}
}
