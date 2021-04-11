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

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class PostGameActionTests extends TestBase {
	
	@Test
	public void when_finishing_game_choose_science_option_works() {
		Game g = setUpGame(finalAgeGameFactory);
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		g.startNextPhase();
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
				
		g.addPostGameAction(p1, new GetOptionsScience());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsScience);
	}
}
