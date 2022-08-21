package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.leaders.Nero;
import com.jtriemstra.wonders.api.model.card.leaders.Solomon;
import com.jtriemstra.wonders.api.model.card.ClayPit;

public class GetOptionsRecruitLeaderUnitTests {
	
	@Test
	public void when_one_playable_card_then_two_options() {
		
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new GetOptionsRecruitLeader())
				.withPlayerCardsInHand("test1", new Card[] {new Solomon(), new Nero()})
				.withPlayerPlayableCards("test1", new CardPlayable[] {
						new CardPlayable(new Solomon(), Status.OK, new ArrayList<>(), 0)
				})
				.withLeaders()
				.buildGame();
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testGame.getPlayer("test1"), testGame);
		
		Assertions.assertEquals(1, r1.getCards().size());
		Assertions.assertEquals("play;discard", r1.getNextActions());
	}
	
	//TODO: more options
	
}
