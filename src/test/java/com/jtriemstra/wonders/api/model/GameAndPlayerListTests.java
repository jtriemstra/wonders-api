package com.jtriemstra.wonders.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class GameAndPlayerListTests extends TestBase {
	
	@Test
	public void when_game_calls_getleftof_return_playerlist() {
		Game g = gameFactory.createGame("test", boardStrategy);
		
		Assertions.assertEquals("test-left", g.getLeftOf(Mockito.mock(Player.class)).getName());
	}

	@TestConfiguration
	static class TestConfig {
		
		@Bean
		@Primary
		@Scope("prototype")
		PlayerList createMockPlayerList() {
			PlayerList mock = Mockito.mock(PlayerList.class);
			Player left = Mockito.mock(Player.class);
			Player right = Mockito.mock(Player.class);
			
			Mockito.when(left.getName()).thenReturn("test-left");
			Mockito.when(right.getName()).thenReturn("test-right");
			Mockito.doReturn(left).when(mock).getLeftOf(Mockito.any());
			Mockito.doReturn(right).when(mock).getRightOf(Mockito.any());
			
			return mock;
		}
	}
}
