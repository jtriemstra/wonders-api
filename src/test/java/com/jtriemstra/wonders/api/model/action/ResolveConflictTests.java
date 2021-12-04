package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ResolveConflictTests extends TestBase {
	
	@Test
	public void when_not_final_turn_no_resolution() {
		setupTest();
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		testPlayer.getArmyFacade().addShields(1);
		
		Assertions.assertEquals(0, testPlayer.getArmyFacade().getVictories().size());
		Assertions.assertEquals(0,  testPlayer.getArmyFacade().getNumberOfVictories(1));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals(0, testPlayer.getArmyFacade().getVictories().size());
		Assertions.assertEquals(0, testPlayer.getArmyFacade().getNumberOfVictories(1));
		
	}

}
