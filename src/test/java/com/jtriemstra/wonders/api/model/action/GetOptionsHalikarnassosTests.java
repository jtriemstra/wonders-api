package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
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
public class GetOptionsHalikarnassosTests extends TestBase {
	
	@Test
	public void when_discard_is_empty_then_next_action_is_wait() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		
		p1.addNextAction(new GetOptionsFromDiscard());
		
		Assertions.assertEquals(0, g.getDiscardCards().length);
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) p1.doAction(r, g);
		
		Assertions.assertEquals("wait", p1.getNextAction().toString());
		Assertions.assertNull(r1.getCards());
	}
	
	@Test
	public void when_discard_has_cards_and_board_is_empty_then_next_action_is_play() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayer(g);
		
		Mockito.doReturn(new Card[] {new StonePit(3,1), new Tavern(4,2)}).when(g).getDiscardCards();
		
		p1.addNextAction(new GetOptionsFromDiscard());
		
		Assertions.assertEquals(2, g.getDiscardCards().length);
		
		OptionsRequest r = new OptionsRequest();
		OptionsResponse r1 = (OptionsResponse) p1.doAction(r, g);
		
		Assertions.assertEquals("playFree", p1.getNextAction().toString());
		Assertions.assertNotNull(r1.getCards());
		Assertions.assertEquals(2, r1.getCards().size());
	}	
}
