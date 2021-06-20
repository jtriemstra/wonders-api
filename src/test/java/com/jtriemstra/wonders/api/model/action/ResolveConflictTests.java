package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ResolveConflictTests extends TestBase {
	
	@Test
	public void when_not_final_turn_no_resolution() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		//g.startNextPhase();
		g.startAge();
		
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
		//g.startNextPhase();
		g.startAge();
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
