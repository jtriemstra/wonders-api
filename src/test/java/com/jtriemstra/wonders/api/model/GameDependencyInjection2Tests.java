package com.jtriemstra.wonders.api.model;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.Giza;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Import(TestBase.TestConfig.class)
public class GameDependencyInjection2Tests {
	
	@Autowired
	@Qualifier("spyBoardStrategy")
	private BoardStrategy spyBoardStrategy;
	
	@Autowired
	Game basicGame;
	
	@Test
	public void when_using_spy_board_factory_then_correct_result_returned() {
		Board b = spyBoardStrategy.getBoard(new BoardSourceBasic(), BoardSide.A_ONLY, new HashSet<>());
		Assertions.assertNotNull(b);
		Assertions.assertTrue(b instanceof Giza);
	}
	
	@Test
	public void when_adding_player_board_factory_is_called() {
		basicGame.addPlayer(Mockito.mock(Player.class));
		// 3 times when the bean is created, and once more above
		Mockito.verify(spyBoardStrategy, Mockito.times(4)).getBoard(Matchers.any(), Matchers.any(), Matchers.any());
	}
	
	
	@TestConfiguration
	static class TestConfig {
									
		@Bean
		@Primary
		BoardStrategy spyBoardStrategy(@Autowired BoardStrategy boardStrategyParam) {
			BoardStrategy spy = Mockito.spy(boardStrategyParam);
			Mockito.doReturn(new Giza(true)).when(spy).getBoard(Matchers.any(), Matchers.any(), Matchers.any());
			return spy;
		}
	}
}

