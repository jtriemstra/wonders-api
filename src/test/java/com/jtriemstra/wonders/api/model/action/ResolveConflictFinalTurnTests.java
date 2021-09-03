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

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, FinalTurnTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ResolveConflictFinalTurnTests extends TestBase {
	
	@Test
	public void when_final_turn_resolution_is_correct() {
		setupTest();
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		testPlayer.addShields(1);
		
		Player p2 = gameWithThreePlayers.getPlayer("test2");
		Player p3 = gameWithThreePlayers.getPlayer("test3");
				
		Assertions.assertEquals(0, testPlayer.getVictories().size());
		Assertions.assertEquals(0,  testPlayer.getNumberOfVictories(1));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertNotNull(testPlayer.getVictories());
		Assertions.assertEquals(2,  testPlayer.getNumberOfVictories(1));
		Assertions.assertEquals(0,  p2.getNumberOfVictories(1));
		Assertions.assertEquals(0,  p3.getNumberOfVictories(1));
		Assertions.assertEquals(0, testPlayer.getNumberOfDefeats());
		Assertions.assertEquals(1, p2.getNumberOfDefeats());
		Assertions.assertEquals(1, p3.getNumberOfDefeats());
	}	
}
