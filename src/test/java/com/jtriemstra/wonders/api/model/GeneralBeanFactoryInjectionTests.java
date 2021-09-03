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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.card.Altar;
import com.jtriemstra.wonders.api.model.card.Card;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GeneralBeanFactoryInjectionTests {

	@Autowired
	DiscardPile discard;
	
	@Autowired
	private GameFactory gameFactory;
	
	@Autowired
	@Qualifier("testGameBean")
	private Game testGameBean;

	@Autowired
	@Qualifier("createNamedBoardStrategy")
	BoardStrategy boardStrategy1;
	
	@Test
	public void discard_pile_is_not_null() {
		Assertions.assertNotNull(discard);
	}
	
	@Test
	public void game_factory_is_not_null() {
		Assertions.assertNotNull(gameFactory);
	}
	
	@Test
	public void game_factory_can_create_game() {
		Game g = createTestGame();
		Assertions.assertNotNull(g);
	}
	
	@Test
	public void game_factory_creates_game_with_mock_discard() {
		Game g = createTestGame();
		Card[] test = g.getDiscardCards();
		Assertions.assertNotNull(test);
		Assertions.assertEquals(1, test.length);
		Assertions.assertTrue(test[0] instanceof Altar);
	}
	
	@Test
	public void game_bean_is_not_null() {
		Assertions.assertNotNull(testGameBean);
	}
	
	@Test
	public void board_strategy_bean_is_not_null() {
		Assertions.assertNotNull(boardStrategy1);
	}
	
	private Game createTestGame() {
		return gameFactory.createGame("test1", 3, false, BoardSide.A_OR_B, false);
	}
	
	@TestConfiguration
	public static class TestConfig {
		@Autowired
		private GameFactory gameFactory;
		
		@Bean
		@Primary
		public DiscardPile getMockDiscardPile() {
			DiscardPile mockDiscard = Mockito.mock(DiscardPile.class);
			Mockito.when(mockDiscard.getCards()).thenReturn(new Card[] {new Altar(1,3)});
			
			return mockDiscard;
		}
		
		@Bean
		@Primary
		public Game testGameBean() {
			return gameFactory.createGame("test2", 3, false, BoardSide.A_OR_B, false);
		}
	}
}
