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

public class DiscardUnitTests {
	
	@Test
	public void when_discarding_then_get_coins_and_lose_card() {
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new Discard())
				.withPlayerCardsInHand("test1", new ClayPit(3,1))
				.build();
		
		DiscardRequest dr = new DiscardRequest();
		dr.setCardName("Clay Pit");
		testGame.getPlayer("test1").doAction(dr, testGame);

		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(1)).addCoinProvider(Mockito.any());
		
		//TODO: either set up a phase condition to do the execution or add more mocks
		
		/*
		 * ((AgePhase)
		 * testGame.getFlow().getCurrentPhase()).handlePostTurnActions(testGame);
		 * 
		 * Assertions.assertEquals(1, testGame.getDiscardCards().length);
		 * Assertions.assertEquals(6, testGame.getPlayer("test1").getHandSize());
		 * Assertions.assertEquals(6, testGame.getPlayer("test1").getCoins());
		 */
		
	}	
}
