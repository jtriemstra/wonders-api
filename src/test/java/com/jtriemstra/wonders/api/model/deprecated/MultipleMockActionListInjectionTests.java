package com.jtriemstra.wonders.api.model.deprecated;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class MultipleMockActionListInjectionTests {
	
	@Autowired
	@Qualifier("mockPlayerFactory")
	PlayerFactory playerFactory;
	
	@Autowired
	@Qualifier("createSpyPlayerFactory")
	PlayerFactory spyPlayerFactory;
	
	@Test
	public void when_using_mock_factory_then_can_mock_action_list_independently() {
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Player p2 = spyPlayerFactory.createPlayer("test2");
		assertEquals("first action list", p1.getNextAction().toString());
		assertEquals("second action list", p2.getNextAction().toString());
	}
		
	@Test
	public void when_using_spy_factory_then_player_can_be_mocked() {
		Player p1 = spyPlayerFactory.createPlayer("test1");
		Mockito.when(p1.getName()).thenReturn("mocked name");
		
		assertEquals("mocked name", p1.getName());
	}
	
	
	@TestConfiguration
	static class TestConfig {
		@Autowired
		PlayerFactory realPlayerFactory;
		
		@Autowired
		Map<String, ActionListFactory> spyActionListFactories;
		
		@Bean
		@Scope("prototype")
		@Primary
		PlayerFactory mockPlayerFactory() {
			//NOTE: spy does not work here because the real player factory is a final class (lambda expression). This ends up being sort of a delegate pattern, with the mock delegating to the real, which seems a little odd, but works so far.
			PlayerFactory mock = Mockito.mock(PlayerFactory.class);
			Mockito.when(mock.createPlayer(Mockito.anyString())).thenAnswer(new Answer() {
			    protected int count = 0;

			    public Object answer(InvocationOnMock invocation) {
			    	count++;

			        return realPlayerFactory.createPlayer("test" + count);
			    }
			});
			
			return mock;
		}	
		
		@Bean
		@Scope("prototype")
		public Player createSpyPlayer(String playerName) {
			ActionListFactory thisFactory = playerName.equals("test2") ? spyActionListFactories.get("createSpyActionListFactory2") : spyActionListFactories.get("createSpyActionListFactory");
			return Mockito.spy(new Player(playerName, thisFactory.createActionList()));
		}
		
		@Bean
		public PlayerFactory createSpyPlayerFactory() {
			return name -> createSpyPlayer(name);
		}
		
		// this nested class is to make these beans available to the parent class. this seems super hacky.
		@TestConfiguration
		static class NestedConfig {
			@Bean
			@Primary // this is to hopefully make the context loader happy, but hopefully I can use both beans
			public ActionListFactory createSpyActionListFactory() {
				return () -> createSpyActionList();
			}
			
			@Bean
			public ActionListFactory createSpyActionListFactory2() {
				return () -> createSpyActionList2();
			}
			
			@Bean
			@Scope("prototype")
			public ActionList createSpyActionList() {
				ActionList spy = Mockito.spy(ActionList.class);
				PossibleActions mockPossible = Mockito.mock(PossibleActions.class);
				Mockito.when(mockPossible.toString()).thenReturn("first action list");
				Mockito.doReturn(mockPossible).when(spy).getNext();
				return spy;
			}
			
			@Bean
			@Scope("prototype")
			public ActionList createSpyActionList2() {
				ActionList spy = Mockito.spy(ActionList.class);
				PossibleActions mockPossible = Mockito.mock(PossibleActions.class);
				Mockito.when(mockPossible.toString()).thenReturn("second action list");
				Mockito.doReturn(mockPossible).when(spy).getNext();
				return spy;
			}
			
		}
	}
}
