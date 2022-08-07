package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.ClayPit;
import com.jtriemstra.wonders.api.model.card.ClayPool;
import com.jtriemstra.wonders.api.model.resource.EvalInfo;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

public class PlayUnitTests {

	@Test
	public void when_playing_no_cost_means_no_payment() {
		CardPlayable[] playables = new CardPlayable[] {new CardPlayable(new ClayPool(3,1), Status.OK, new ArrayList<>(), 0)};
		
		UnitTestCaseBuilder testBuilder = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsInHand("test1", new ClayPool(3,1));
		Game testGame = 
				testBuilder.withPlayerNextAction("test1", new Play(Arrays.asList(playables), cardName -> testBuilder.getPlayer("test1").removeCardFromHand(cardName)))
				.build();
		
		ActionRequest r = new PlayRequest();
		r.setCardName("Clay Pool");
		Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(0)).gainCoins(Mockito.anyInt());
	}

	@Test
	public void when_building_with_cost_schedules_payment() {
			
		Game testGame =  
				UnitTestCaseBuilder.create()
				.withPlayerCardsInHand("test1", new ClayPit(3,1))
				.build();

		IPlayer test1 = testGame.getPlayer("test1"), test2=testGame.getPlayer("test2"), test3=testGame.getPlayer("test3");
		
		List<TradeCost> costs = Arrays.asList(new TradeCost[] { (new TradingResourceEvaluator2(new ArrayList<EvalInfo>(), 0, null)).new TradeCost(Collections.singletonMap("Left", 1))});
		CardPlayable[] playables = new CardPlayable[] {new CardPlayable(new ClayPit(3,1), Status.OK, costs, 0)};
		playables[0].setPaymentFunction( 
				(p,g) -> {
					int leftCost = 1;
					int rightCost = 0;
					
					if (leftCost > 0) {
						p.gainCoins(-1 * leftCost);
						test2.gainCoins(leftCost);
						p.eventNotify("trade.neighbor");
					}
					if (rightCost > 0) {
						p.gainCoins(-1 * rightCost);
						test3.gainCoins(rightCost);
						p.eventNotify("trade.neighbor");
					}
				}
		);
		BaseAction play = new Play(Arrays.asList(playables), cardName -> testGame.getPlayer("test1").removeCardFromHand(cardName));
		testGame.getPlayer("test1").addNextAction(play);
		
		ActionRequest r = new PlayRequest();
		r.setCardName("Clay Pit");
		Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(1)).gainCoins(Mockito.anyInt());
	}
	
	//TODO: test additional play scenarios
}
