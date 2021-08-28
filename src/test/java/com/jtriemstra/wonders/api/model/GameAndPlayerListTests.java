package com.jtriemstra.wonders.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.BoardManagerFactory;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameAndPlayerListTests extends TestBase {

	@Autowired
	private BoardManagerFactory boardManagerFactory;
	
	@Test
	public void when_game_calls_getleftof_return_playerlist() {
		CardFactory guildFactory = new GuildCardFactoryBasic();
		DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
		GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
		BoardSource boardSource = new BoardSourceBasic();
		BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
		
		Game g = gameFactory.createGame("test", 3, new GameFlow(phaseFactory), boardManager);
		
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
