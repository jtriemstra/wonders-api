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
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.request.WaitRequest;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.Babylon;
import com.jtriemstra.wonders.api.model.phases.AgePhase;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Babylon-B;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, FinalTurnTestConfiguration.class, TestBase.TestStateConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetOptionsBabylonFinalTurnTests extends TestBase {
	
	@Test
	public void when_final_turn_then_return_something() {
		setupTest();

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		Babylon b = new Babylon(false);
		testPlayer.addNextAction(b.new GetOptionsBabylon());
		
		Assertions.assertTrue(gameWithThreePlayers.getFlow().isFinalTurn());
		
		OptionsRequest r = new OptionsRequest();
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertNotEquals("wait", testPlayer.getNextAction().toString());
		Assertions.assertFalse(r1 instanceof WaitResponse);
	}
	
	@Test
	public void when_final_turn_and_have_card_then_can_play() {
		setupTest();

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		Babylon b = new Babylon(false);
		testPlayer.addNextAction(b.new GetOptionsBabylon());
				
		Assertions.assertTrue(gameWithThreePlayers.getFlow().isFinalTurn());
		
		OptionsRequest r = new OptionsRequest();
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("play;discard", testPlayer.getNextAction().toString());
		Assertions.assertFalse(r1 instanceof WaitResponse);
	}	

	// the following tests are trying to repro a situation where the UI never presented the Babylon options for the 7th card, but also never presented the age complete UI. Have been unable to repro that manually so far
	@Test
	public void when_final_turn_and_have_card_then_can_play_integration_test_wait_first() {
		setupTest();

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		Babylon b = new Babylon(false);
		gameWithThreePlayers.getFlow().addPostTurnAction(testPlayer, b.new GetOptionsBabylon(), (phase, flow) -> {return phase instanceof AgePhase;});
						
		//sanity check for the test itself
		Assertions.assertTrue(gameWithThreePlayers.getFlow().isFinalTurn());
		Assertions.assertTrue(gameWithThreePlayers.getFlow().getCurrentPhase() instanceof AgePhase);
		
		WaitRequest r = new WaitRequest();
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
				
		Assertions.assertEquals("options", r1.getNextActions());		
	}


	@Test
	public void when_final_turn_and_have_card_then_can_play_integration_test_wait_second() {
		setupTest();

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		Babylon b = new Babylon(false);
		gameWithThreePlayers.getFlow().addPostTurnAction(testPlayer, b.new GetOptionsBabylon(), (phase, flow) -> {return phase instanceof AgePhase;});
						
		//sanity check for the test itself
		Assertions.assertTrue(gameWithThreePlayers.getFlow().isFinalTurn());
		Assertions.assertTrue(gameWithThreePlayers.getFlow().getCurrentPhase() instanceof AgePhase);
		
		WaitRequest r = new WaitRequest();
		BaseResponse r2 = gameWithThreePlayers.getPlayer("test2").doAction(r, gameWithThreePlayers);
		Assertions.assertEquals("wait", r2.getNextActions());
		
		BaseResponse r1 = gameWithThreePlayers.getPlayer("test1").doAction(r, gameWithThreePlayers);
		Assertions.assertEquals("options", r1.getNextActions());
		
		r2 = gameWithThreePlayers.getPlayer("test2").doAction(r, gameWithThreePlayers);
		Assertions.assertEquals("wait", r2.getNextActions());
	}


	@Test
	public void when_final_turn_and_have_card_then_can_play_integration_test_wait_third() {
		setupTest();

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		Babylon b = new Babylon(false);
		gameWithThreePlayers.getFlow().addPostTurnAction(testPlayer, b.new GetOptionsBabylon(), (phase, flow) -> {return phase instanceof AgePhase;});
						
		//sanity check for the test itself
		Assertions.assertTrue(gameWithThreePlayers.getFlow().isFinalTurn());
		Assertions.assertTrue(gameWithThreePlayers.getFlow().getCurrentPhase() instanceof AgePhase);

		WaitRequest r = new WaitRequest();
		BaseResponse r2 = gameWithThreePlayers.getPlayer("test2").doAction(r, gameWithThreePlayers);
		Assertions.assertEquals("wait", r2.getNextActions());
		
		BaseResponse r3 = gameWithThreePlayers.getPlayer("test3").doAction(r, gameWithThreePlayers);
		Assertions.assertEquals("wait", r3.getNextActions());
		
		BaseResponse r1 = gameWithThreePlayers.getPlayer("test1").doAction(r, gameWithThreePlayers);
		Assertions.assertEquals("options", r1.getNextActions());
		
		r3 = gameWithThreePlayers.getPlayer("test3").doAction(r, gameWithThreePlayers);
		Assertions.assertEquals("wait", r3.getNextActions());
		
		r2 = gameWithThreePlayers.getPlayer("test2").doAction(r, gameWithThreePlayers);
		Assertions.assertEquals("wait", r2.getNextActions());
	}
}
