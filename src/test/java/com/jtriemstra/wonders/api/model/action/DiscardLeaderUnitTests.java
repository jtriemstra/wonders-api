package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.card.ClayPit;
import com.jtriemstra.wonders.api.model.phases.AgePhase;

public class DiscardLeaderUnitTests {
	
	@Test
	public void when_discarding_then_get_coins_and_lose_card() {
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new DiscardLeader())
				.withPlayerCardsInHand("test1", new ClayPit(3,1))
				.build();
		
		DiscardRequest dr = new DiscardRequest();
		dr.setCardName("Clay Pit");
		
		Assertions.assertEquals(0, testGame.getPlayer("test1").getCoins());
		Assertions.assertEquals(1, testGame.getPlayer("test1").getHandSize());
		
		testGame.getPlayer("test1").doAction(dr, testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Assertions.assertEquals(3, testGame.getPlayer("test1").getCoins());
		Assertions.assertEquals(0, testGame.getPlayer("test1").getHandSize());
		Assertions.assertEquals(0, testGame.getDiscardCards().length);
		
	}	
}
