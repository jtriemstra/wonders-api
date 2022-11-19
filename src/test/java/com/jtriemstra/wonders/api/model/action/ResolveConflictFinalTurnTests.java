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
import com.jtriemstra.wonders.api.model.IPlayer;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, FinalTurnTestConfiguration.class, TestBase.TestStateConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ResolveConflictFinalTurnTests extends TestBase {
	
	@Test
	public void when_final_turn_resolution_is_correct() {
		setupTest();
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		testPlayer.getArmyFacade().addShields(1);
		
		IPlayer p2 = gameWithThreePlayers.getPlayer("test2");
		IPlayer p3 = gameWithThreePlayers.getPlayer("test3");
				
		Assertions.assertEquals(0, testPlayer.getArmyFacade().getVictories().size());
		Assertions.assertEquals(0,  testPlayer.getArmyFacade().getVictories(1).size());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertNotNull(testPlayer.getArmyFacade().getVictories());
		Assertions.assertEquals(2,  testPlayer.getArmyFacade().getVictories(1).size());
		Assertions.assertEquals(0,  p2.getArmyFacade().getVictories(1).size());
		Assertions.assertEquals(0,  p3.getArmyFacade().getVictories(1).size());
		Assertions.assertEquals(0, testPlayer.getArmyFacade().getNumberOfDefeats());
		Assertions.assertEquals(1, p2.getArmyFacade().getNumberOfDefeats());
		Assertions.assertEquals(1, p3.getArmyFacade().getNumberOfDefeats());
	}	
}
