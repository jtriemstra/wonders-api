package com.jtriemstra.wonders.api.model.action;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ActionRequest;
import com.jtriemstra.wonders.api.dto.request.ShowLeaderRequest;
import com.jtriemstra.wonders.api.dto.response.AdditionalLeaderResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.Nero;
import com.jtriemstra.wonders.api.model.card.leaders.Solomon;

public class ShowAdditionalLeadersUnitTests {

	@Test
	public void when_creating_with_cards_they_show_up_in_response() {
		
		Card[] leaders = new Card[] { new Solomon(), new Nero()};
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new ShowAdditionalLeaders(Arrays.asList(leaders)))
				.buildGame();
		
		ActionRequest r = new ShowLeaderRequest();
		AdditionalLeaderResponse r1 = (AdditionalLeaderResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(2, r1.getNewLeaders().size());
	}

}
