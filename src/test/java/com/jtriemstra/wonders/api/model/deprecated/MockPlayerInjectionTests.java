package com.jtriemstra.wonders.api.model.deprecated;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.jtriemstra.wonders.api.model.deprecated.ActionListFactory;
import com.jtriemstra.wonders.api.model.deprecated.Player;
import com.jtriemstra.wonders.api.model.deprecated.PlayerFactory;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class MockPlayerInjectionTests {
	
	@Autowired
	@Qualifier("mockPlayerFactory")
	PlayerFactory playerFactory;
	
	@Autowired
	@Qualifier("createSpyPlayerFactory")
	PlayerFactory spyPlayerFactory;
	
	@Test
	public void when_using_mock_factory_then_names_are_auto_created() {
		Player p1 = playerFactory.createPlayer("unused-string");
		Player p2 = playerFactory.createPlayer("unused-string");
		assertEquals("test1", p1.getName());
		assertEquals("test2", p2.getName());
	}
	
	@Test
	public void when_using_mock_factory_then_action_list_is_injected() {
		Player p = playerFactory.createPlayer("unused-string");
		
		//NOTE: this test will fail if a bean gets added to the context which does mocking or setup on the action list
		Throwable exceptionThatWasThrown = assertThrows(RuntimeException.class, () -> {
		    p.getNextAction();
		});

		assertEquals("tried to get next action, but there are zero actions available", exceptionThatWasThrown.getMessage());
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
		ActionListFactory realActionListFactory;
		
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
			return Mockito.spy(new Player(playerName, realActionListFactory.createActionList()));
		}
		
		@Bean
		public PlayerFactory createSpyPlayerFactory() {
			return name -> createSpyPlayer(name);
		}
		
		
	}
}
