package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.BuildRequest;
import com.jtriemstra.wonders.api.dto.request.TradingInfo;
import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.board.Ephesus;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.ClayPit;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

public class BuildUnitTests {

	@Test
	public void when_building_no_cost_means_no_payment() {
		UnitTestCaseBuilder testBuilder = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsInHand("test1", new ClayPit(3,1));
		Game testGame = 
				testBuilder.withPlayerNextAction("test1", new Build(new Buildable((new Ephesus(true)).new A1(), Status.OK, 0, 0, 0), cardName -> testBuilder.getPlayer("test1").removeCardFromHand(cardName)))
				.build();
		
		ActionRequest r = new BuildRequest();
		r.setCardName("Clay Pit");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(0)).gainCoins(Mockito.anyInt());
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(0)).eventNotify(Mockito.any());
		Assertions.assertEquals(1, testGame.getPlayer("test1").getNumberOfBuiltStages());
		Assertions.assertEquals(0, testGame.getPlayer("test1").getHandSize());
	}

	@Test
	public void when_building_with_cost_schedules_payment() {
		UnitTestCaseBuilder testBuilder = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsInHand("test1", new ClayPit(3,1));
		Game testGame = 
				testBuilder.withPlayerNextAction("test1", new Build(new Buildable((new Ephesus(true)).new A1(), Status.OK, 1, 1, 0), cardName -> testBuilder.getPlayer("test1").removeCardFromHand(cardName)))
				.build();
		
		ActionRequest r = new BuildRequest();
		r.setCardName("Clay Pit");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(1)).gainCoins(Mockito.anyInt());
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(1)).eventNotify("trade.neighbor");
		Assertions.assertEquals(1, testGame.getPlayer("test1").getNumberOfBuiltStages());
		Assertions.assertEquals(0, testGame.getPlayer("test1").getHandSize());
	}

	@Test
	public void when_building_with_cost_options_schedules_payment() {
		TradingResourceEvaluator2 x = new TradingResourceEvaluator2(new ArrayList<ResourceSet>(), new ArrayList<ResourceSet>(), new ArrayList<ResourceSet>(), 0, null, null);
		
		List<TradeCost> costs = Arrays.asList(new TradeCost[] { x.new TradeCost(1,2), x.new TradeCost(2,1)});
		
		UnitTestCaseBuilder testBuilder = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsInHand("test1", new ClayPit(3,1));
		Game testGame = 
				testBuilder.withPlayerNextAction("test1", new Build(new Buildable((new Ephesus(true)).new A1(), Status.OK, costs), cardName -> testBuilder.getPlayer("test1").removeCardFromHand(cardName)))
				.build();
		
		ActionRequest r = new BuildRequest();
		r.setCardName("Clay Pit");
		TradingInfo t = new TradingInfo();
		t.setPlayableIndex(1);
		r.setTradingInfo(t);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(2)).gainCoins(Mockito.anyInt());
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(2)).eventNotify("trade.neighbor");
		Assertions.assertEquals(1, testGame.getPlayer("test1").getNumberOfBuiltStages());
		Assertions.assertEquals(0, testGame.getPlayer("test1").getHandSize());
	}
	
}
