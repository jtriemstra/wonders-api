package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ChooseScienceRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.card.ScienceType;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class ChooseScienceUnitTests {
	
	@Test
	public void when_choosing_science_then_added_to_player() {
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new ChooseScience())
				.build();
				
		ChooseScienceRequest r = new ChooseScienceRequest();
		r.setOptionName(ScienceType.TABLET);
		BaseResponse r1 = testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals("wait", testGame.getPlayer("test1").getNextAction().toString());
		Assertions.assertTrue(r1 instanceof ActionResponse);
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}	

}
