package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.FinalTurnFinalAgeTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.IPlayer;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, FinalTurnFinalAgeTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostTurnActionFinalAgeTurnTests extends TestBase {
	
	@Test
	public void when_finishing_final_age_turn_with_defaults_and_hali_action_wait_for_hali_options() {
		setupTest();
		IPlayer p2 = gameWithThreePlayers.getPlayer("test2");
		IPlayer p3 = gameWithThreePlayers.getPlayer("test3");
				
		gameWithThreePlayers.getFlow().addPostTurnAction(testPlayer, new GetOptionsFromDiscard(), (p,g) -> true);

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("options", testPlayer.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(testPlayer.getNextAction().getByName("options") instanceof GetOptionsFromDiscard);
	}
	
}
