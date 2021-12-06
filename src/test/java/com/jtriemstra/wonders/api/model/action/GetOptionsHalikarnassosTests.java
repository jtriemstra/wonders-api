package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.StonePit;
import com.jtriemstra.wonders.api.model.card.Tavern;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetOptionsHalikarnassosTests extends TestBase {
	
	@Test
	public void when_discard_is_empty_then_next_action_is_wait() {
		setupTest();
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		testPlayer.addNextAction(new GetOptionsFromDiscard());
		
		Assertions.assertEquals(0, gameWithThreePlayers.getDiscardCards().length);
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("wait", testPlayer.getNextAction().toString());
		Assertions.assertNull(r1.getCards());
	}
	
	@Test
	public void when_discard_has_cards_and_board_is_empty_then_next_action_is_play() {
		setupTest();
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		Game g = Mockito.spy(gameWithThreePlayers);
				
		Mockito.doReturn(new Card[] {new StonePit(3,1), new Tavern(4,2)}).when(g).getDiscardCards();
		
		testPlayer.addNextAction(new GetOptionsFromDiscard());
		
		Assertions.assertEquals(2, g.getDiscardCards().length);
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) Player.doAction(r, testPlayer, g);
		
		Assertions.assertEquals("playFree", testPlayer.getNextAction().toString());
		Assertions.assertNotNull(r1.getCards());
		Assertions.assertEquals(2, r1.getCards().size());
	}	
}
