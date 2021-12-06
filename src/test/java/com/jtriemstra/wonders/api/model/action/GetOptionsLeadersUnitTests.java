package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.Nero;
import com.jtriemstra.wonders.api.model.card.leaders.Solomon;

public class GetOptionsLeadersUnitTests {
	
	@Test
	public void when_two_cards_then_two_options() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptionsLeaders())
				.withPlayerCardsInHand("test1", new Card[] {new Solomon(), new Nero()})
				.withLeaders()
				.build();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(2, r1.getCards().size());
		Assertions.assertEquals("keepLeader", r1.getNextActions());
	}
	
	//TODO: more options
	
}
