package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ChooseBoardRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.MagistratesGuild;

public class ChooseBoardUnitTests {
	
	private ChooseBoardRequest createRequest(boolean skip, String name, String side) {
		ChooseBoardRequest r = new ChooseBoardRequest();
		r.setSkip(skip);
		r.setBoardName(name);
		r.setBoardSide(side);
		return r;
	}
	
	@Test
	public void when_choosing_board_skip_changes_nothing() {
		
		UnitTestCaseBuilder testBuilder = 
				UnitTestCaseBuilder.create()
				.withInitialBoards("Ephesus-A;Giza-A;Babylon-A");
				
		Game testGame = testBuilder.withPlayerNextAction("test1", new ChooseBoard(testBuilder.getBoardManager()))
				.build();
		
		ChooseBoardRequest r = createRequest(true, "", "");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals("Ephesus", testGame.getPlayer("test1").getBoardName());
		
	}	

	@Test
	public void when_choosing_board_name_changes() {
		
		UnitTestCaseBuilder testBuilder = 
				UnitTestCaseBuilder.create()
				.withInitialBoards("Ephesus-A;Giza-A;Babylon-A");
				
		Game testGame = testBuilder.withPlayerNextAction("test1", new ChooseBoard(testBuilder.getBoardManager()))
				.build();
		
		ChooseBoardRequest r = createRequest(false, "Rhodes", "A");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals("Rhodes", testGame.getPlayer("test1").getBoardName());
		
	}

	@Test
	public void when_choosing_board_must_not_be_in_use() {
		
		UnitTestCaseBuilder testBuilder = 
				UnitTestCaseBuilder.create()
				.withInitialBoards("Ephesus-A;Giza-A;Babylon-A");
				
		Game testGame = testBuilder.withPlayerNextAction("test1", new ChooseBoard(testBuilder.getBoardManager()))
				.build();
		
		ChooseBoardRequest r = createRequest(false, "Giza", "A");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals("Ephesus", testGame.getPlayer("test1").getBoardName());
		
	}
}
