package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.phases.Phase;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, TestBase.TestStateConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostTurnActionsTests extends TestBase {
		
	String dummyResults = "";

	@Test
	public void when_wait_for_turn_execute_actions_in_order() {
		dummyResults = "";
		setupTest();
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		gameWithThreePlayers.getFlow().addPostTurnAction(null, new DummyPostTurnAction2(), (p, g) -> true);
		gameWithThreePlayers.getFlow().addPostTurnAction(null, new DummyPostTurnAction1(), (p, g) -> true);
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("12", dummyResults);
	}
	
	
	public class DummyPostTurnAction1 implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {			
			return 1;
		}
		
		@Override
		public String getName() {
			return "dummy1";
		}

		@Override
		public ActionResponse execute(Game game) {
			dummyResults += "1";
			return null;
		}
		
	}
	
	public class DummyPostTurnAction2 implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {			
			return 2;
		}
		
		@Override
		public String getName() {
			return "dummy2";
		}

		@Override
		public ActionResponse execute(Game game) {
			dummyResults += "2";
			return null;
		}
		
	}
}
