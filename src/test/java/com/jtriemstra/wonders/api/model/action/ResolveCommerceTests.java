package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Ages;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.resource.TradingPayment;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})	
@Import(TestBase.TestConfig.class)
public class ResolveCommerceTests extends TestBase {
		
	@Test
	public void when_not_final_turn_commerce_happens() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");

		g.startNextPhase();
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
		Game g = setUpGame(finalTurnGameFactory);
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		
		g.startNextPhase();
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		p1.schedulePayment(new TradingPayment(2, p1, p2));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals(1, p1.getCoins());
		Assertions.assertEquals(5, p2.getCoins());
	}
	
}
