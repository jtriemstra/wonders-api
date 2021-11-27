package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BuildRequest;
import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.board.Ephesus;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.ClayPit;

public class BuildUnitTests {

	@Test
	public void when_building_no_cost_means_no_payment() {
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsInHand("test1", new ClayPit(3,1))
				.withPlayerNextAction("test1", new Build(new Buildable((new Ephesus(true)).new A1(), Status.OK, 0, 0, 0)))
				.build();
		
		ActionRequest r = new BuildRequest();
		r.setCardName("Clay Pit");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(0)).gainCoins(Mockito.anyInt());
	}

	@Test
	public void when_building_with_cost_schedules_payment() {
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsInHand("test1", new ClayPit(3,1))
				.withPlayerNextAction("test1", new Build(new Buildable((new Ephesus(true)).new A1(), Status.OK, 1, 1, 0)))
				.build();
		
		ActionRequest r = new BuildRequest();
		r.setCardName("Clay Pit");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(1)).gainCoins(Mockito.anyInt());
	}
	
	//TODO: test additional build scenarios
}
