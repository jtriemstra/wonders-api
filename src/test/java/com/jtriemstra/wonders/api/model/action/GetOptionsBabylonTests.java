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
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.Babylon;
import com.jtriemstra.wonders.api.model.card.StonePit;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, TestBase.TestStateConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetOptionsBabylonTests extends TestBase {
	
	@Test
	public void when_not_final_turn_then_do_nothing() {
		setupTest();
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		Babylon b = new Babylon(false);
		testPlayer.addNextAction(b.new GetOptionsBabylon());
		
		Assertions.assertFalse(gameWithThreePlayers.getFlow().isFinalTurn());
		
		OptionsRequest r = new OptionsRequest();
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("wait", testPlayer.getNextAction().toString());
		Assertions.assertTrue(r1 instanceof WaitResponse);
	}
	
}
