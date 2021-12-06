package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.ClayPit;

public class GetOptionsFromDiscardUnitTests {
	
	@Test
	public void when_no_discards_then_no_options() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptionsFromDiscard())
				.build();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertNull(r1.getCards());
		Assertions.assertNotNull(r1.getMessage());
	}
	
	@Test
	public void when_discards_then_options() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withDiscardCards(new Card[] { new ClayPit(3,1)})
				.withPlayerNextAction("test1", new GetOptionsFromDiscard())
				.build();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(1, r1.getCards().size());
	}
	
	//TODO: more options
	
}
