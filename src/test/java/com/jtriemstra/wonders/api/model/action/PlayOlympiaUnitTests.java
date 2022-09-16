package com.jtriemstra.wonders.api.model.action;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.PlayFreeRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.ClayPit;

public class PlayOlympiaUnitTests {

	@Test
	public void when_playing_no_cost_means_no_payment() {
		HashSet<Integer> used = new HashSet<>();
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsInHand("test1", new ClayPit(3,1))
				.withPlayerNextAction("test1", new PlayOlympia(new String[] {"Clay Pit"}, used))
				.buildGame();
		
		ActionRequest r = new PlayFreeRequest();
		r.setCardName("Clay Pit");
		testGame.getPlayer("test1").doAction(r, testGame);
		
		testGame.getPlayer("test1").doScheduledAction();
		
		Mockito.verify(testGame.getPlayer("test1"), Mockito.times(0)).gainCoins(Mockito.anyInt());
		Assertions.assertTrue(testGame.getPlayer("test1").hasPlayedCard(new ClayPit(3,1)));
		Assertions.assertEquals(1, used.size());
	}

	//TODO: add validation on usedAges and throw Exception
}
