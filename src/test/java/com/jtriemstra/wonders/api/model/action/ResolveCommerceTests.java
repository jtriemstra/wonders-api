package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.TradingPayment;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})	
@Import(TestBase.TestConfig.class)
public class ResolveCommerceTests extends TestBase {
		
	@Test
	public void when_not_final_turn_commerce_happens() {
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p1 = getPresetPlayer(g);
		Player p2 = g.getPlayer("test2");

		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		p1.schedulePayment(new TradingPayment(2, p1, p2));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals(1, p1.getCoins());
		Assertions.assertEquals(5, p2.getCoins());
	}

	@Test
	public void when_final_turn_commerce_happens() {
		Game g = setUpGameWithPlayerAndNeighbors(finalTurnGameFactory);
		Player p1 = getPresetPlayer(g);
		Player p2 = g.getPlayer("test2");
				
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		p1.schedulePayment(new TradingPayment(2, p1, p2));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals(1, p1.getCoins());
		Assertions.assertEquals(5, p2.getCoins());
	}
	
}
