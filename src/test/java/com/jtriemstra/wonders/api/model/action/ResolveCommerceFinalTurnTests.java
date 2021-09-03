package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.FinalTurnTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.TradingPayment;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})	
@Import({TestBase.TestConfig.class, FinalTurnTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ResolveCommerceFinalTurnTests extends TestBase {
	
	@Test
	public void when_final_turn_commerce_happens() {
		setupTest();
		Player p2 = gameWithThreePlayers.getPlayer("test2");

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		testPlayer.schedulePayment(new TradingPayment(2, testPlayer, p2));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals(1, testPlayer.getCoins());
		Assertions.assertEquals(5, p2.getCoins());
	}
	
}
