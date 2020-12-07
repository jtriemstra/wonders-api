package com.jtriemstra.wonders.api.model;

import org.junit.jupiter.api.Assertions;
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

import com.jtriemstra.wonders.api.model.action.Wait;
import com.jtriemstra.wonders.api.model.board.BoardFactory;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GameNotifyWaitingFalseTests {
	
	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
	
	@Test
	public void when_notify_waiting_for_turn_return_false() {
		Game g = gameFactory.createGame("test", boardFactory);
		
		Assertions.assertEquals(false, g.notifyWaiting(Wait.For.TURN));
	}

	@TestConfiguration
	static class TestConfig {
		
		@Bean
		@Primary
		@Scope("prototype")
		PlayerList createMockPlayerList() {
			PlayerList mock = Mockito.mock(PlayerList.class);
			Mockito.when(mock.allWaiting()).thenReturn(false);
			return mock;
		}
	}
}
