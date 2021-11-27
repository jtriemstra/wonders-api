package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ListBoardsRequest;
import com.jtriemstra.wonders.api.dto.response.ListBoardResponse;
import com.jtriemstra.wonders.api.model.Game;

public class ListBoardsUnitTests {
		
	@Test
	public void when_valid_info_then_no_exception() {
		
		UnitTestCaseBuilder testBuilder = UnitTestCaseBuilder.create()
				.withInitialBoards("Ephesus-A;Giza-A;Babylon-A");
				
		Game testGame = testBuilder.withPlayerNextAction("test1", new ListBoards(testBuilder.getBoardManager()))
				.build();
		
		ListBoardsRequest r = new ListBoardsRequest();
		ListBoardResponse r1 = (ListBoardResponse) testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertTrue(r1.getBoards().get("Ephesus"));
		Assertions.assertFalse(r1.getBoards().get("Rhodes"));
		
	}
		
}
