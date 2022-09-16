package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.KeepLeaderRequest;
import com.jtriemstra.wonders.api.dto.response.DiscardResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.leaders.Nero;
import com.jtriemstra.wonders.api.model.card.leaders.Solomon;

public class KeepLeaderUnitTests {
	
	private List<CardPlayable> convertCardsToPlayable(Card...cards) {
		List<CardPlayable> result = new ArrayList<>();
		for (Card c : cards) {
			result.add(new CardPlayable(c, Status.OK, new ArrayList<>(), 0));
		}
		
		return result;
	}
	
	@Test
	public void when_valid_info_then_no_exception() {
		Card[] currentCards = new Card[] {new Solomon(), new Nero()};
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new KeepLeader(convertCardsToPlayable(currentCards)))
				.withPlayerCardsInHand("test1", currentCards)
				.withLeaders()
				.buildGame();
		
		KeepLeaderRequest r = new KeepLeaderRequest();
		r.setCardName("Nero");
		DiscardResponse r1 = (DiscardResponse) testGame.getPlayer("test1").doAction(r, testGame);
		
	}
		
}
