package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.FinalTurnFinalAgeTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.PostTurnActionFactoryDefaultFactory;
import com.jtriemstra.wonders.api.model.phases.GameFlow;
import com.jtriemstra.wonders.api.model.phases.Phase;
import com.jtriemstra.wonders.api.model.phases.PostTurnActionFactoryDefault;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, FinalTurnFinalAgeTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostGameActionsTests extends TestBase {

	String dummyResults = "";
	
	@Test
	public void when_wait_for_turn_execute_postgame_actions_in_order() {
		dummyResults = "";
		testPlayer = gameWithThreePlayers.getPlayer("test1");
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		gameWithThreePlayers.getFlow().addPostGameAction(null, new DummyPostGameAction1(), Phase.class);
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		
		Assertions.assertEquals("game1", dummyResults);
	}
	
	public class DummyPostGameAction1 implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {			
			return 1;
		}
		
		@Override
		public String getName() {
			return "dummyGame1";
		}

		@Override
		public ActionResponse execute(Game game) {
			dummyResults += "game1";
			return null;
		}
		
	}
	
}
