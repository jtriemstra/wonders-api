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
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.StonePit;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DiscardTests extends TestBase {
	
	@Test
	public void when_discarding_then_get_coins_and_lose_card() {
		setupTest();
		String cardName = testPlayer.getHand().getAll()[0].getName();
		
		testPlayer.addNextAction(new Discard());
		
		Assertions.assertEquals(0, gameWithThreePlayers.getDiscardCards().length);
		Assertions.assertEquals(7, testPlayer.getHandSize());
		Assertions.assertEquals(3, testPlayer.getCoins());
		
		DiscardRequest dr = new DiscardRequest();
		dr.setCardName(cardName);
		testPlayer.doAction(dr, gameWithThreePlayers);
		
		Assertions.assertEquals(1, gameWithThreePlayers.getDiscardCards().length);
		Assertions.assertEquals(6, testPlayer.getHandSize());
		Assertions.assertEquals(3, testPlayer.getCoins());
		Mockito.verify(testPlayer, Mockito.times(1)).setCoinProvider(Mockito.any());
	}	
}
