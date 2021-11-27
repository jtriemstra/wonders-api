package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.OptionsScienceResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;

public class GetOptionsScienceUnitTests {
	
	@Test
	public void when_not_final_turn_then_wait() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptionsScience())
				.build();
		
		OptionsRequest r = new OptionsRequest();
		WaitResponse r1 = (WaitResponse) testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals("wait", r1.getNextActions());
	}

	@Test
	public void when_final_turn_then_three_options() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptionsScience())
				.withFinalAgeAndTurn()
				.build();
		
		OptionsRequest r = new OptionsRequest();
		OptionsScienceResponse r1 = (OptionsScienceResponse) testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals("chooseScience", r1.getNextActions());
	}
		
}
