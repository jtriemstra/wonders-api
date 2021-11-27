package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.ClayPit;

public class GetOptionsUnitTests {
	
	@Test
	public void when_one_playable_card_then_two_options() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptions())
				.withPlayerPlayableCards("test1", new CardPlayable[] {
						new CardPlayable(new ClayPit(3,1), Status.OK, 0, 0, 0)
				})
				.build();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, r1.getCards().size());
		Assertions.assertEquals("play;discard", r1.getNextActions());
	}
	
	//TODO: more options
	
}
