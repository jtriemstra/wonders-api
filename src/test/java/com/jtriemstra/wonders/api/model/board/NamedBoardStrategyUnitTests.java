package com.jtriemstra.wonders.api.model.board;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NamedBoardStrategyUnitTests {
	@Test
	public void when_invalid_board_name_then_exception() {
		NamedBoardStrategy nbf = new NamedBoardStrategy("garbage-A;garbage-A;garbage-A");
		
		Throwable exceptionThrown = Assertions.assertThrows(RuntimeException.class, () -> {
			nbf.getBoard(new BoardSourceBasic(), BoardSide.A_OR_B, new HashSet<>());
		});
		
		Assertions.assertEquals("board not found", exceptionThrown.getMessage());
	}
	
	@Test
	public void when_valid_name_then_object_returned() {
		NamedBoardStrategy nbf = new NamedBoardStrategy("Ephesus-A;garbage-A;garbage-A");
		
		Board b = nbf.getBoard(new BoardSourceBasic(), BoardSide.A_OR_B, new HashSet<>());
		
		Assertions.assertNotNull(b);
		Assertions.assertTrue(b instanceof Ephesus);
	
	}
}
