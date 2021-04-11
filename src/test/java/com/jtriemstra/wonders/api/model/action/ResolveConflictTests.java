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
import com.jtriemstra.wonders.api.model.Game.DiscardFinalCardAction;
import com.jtriemstra.wonders.api.model.Game.PlayCardsAction;
import com.jtriemstra.wonders.api.model.Game.ResolveCommerceAction;
import com.jtriemstra.wonders.api.model.Game.ResolveConflictAction;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class ResolveConflictTests extends TestBase {
	
	@Test
	public void when_not_final_turn_no_resolution() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		p1.addShields(1);
		
		Assertions.assertEquals(0, p1.getVictories().size());
		Assertions.assertEquals(0,  p1.getNumberOfVictories(1));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals(0, p1.getVictories().size());
		Assertions.assertEquals(0,  p1.getNumberOfVictories(1));
		
	}

	@Test
	public void when_final_turn_resolution_is_correct() {
		Game g = setUpGame(finalTurnGameFactory);
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		g.startNextPhase();
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		p1.addShields(1);
		
		Assertions.assertEquals(0, p1.getVictories().size());
		Assertions.assertEquals(0,  p1.getNumberOfVictories(1));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertNotNull(p1.getVictories());
		Assertions.assertEquals(2,  p1.getNumberOfVictories(1));
		Assertions.assertEquals(0,  p2.getNumberOfVictories(1));
		Assertions.assertEquals(0,  p3.getNumberOfVictories(1));
		Assertions.assertEquals(0, p1.getNumberOfDefeats());
		Assertions.assertEquals(1, p2.getNumberOfDefeats());
		Assertions.assertEquals(1, p3.getNumberOfDefeats());
	}	
}
