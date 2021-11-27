package com.jtriemstra.wonders.api.model.action;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.PlayFreeRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.ClayPit;

public class PlayHalikarnassosUnitTests {

	@Test
	public void when_playing_no_cost_means_no_payment() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withDiscardCards(new Card[] {new ClayPit(3,1)} )
				.withPlayerCardsInHand("test1", new ClayPit(3,1))
				.withPlayerNextAction("test1", new PlayHalikarnassos(new String[] {"Clay Pit"}))
				.build();
		
		ActionRequest r = new PlayFreeRequest();
		r.setCardName("Clay Pit");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(0)).gainCoins(Mockito.anyInt());
		Assertions.assertTrue(testGame.getPlayer("test1").hasPlayedCard(new ClayPit(3,1)));
	}
	
	//TODO: test additional play scenarios
}
