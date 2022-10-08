package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.board.Babylon;
import com.jtriemstra.wonders.api.model.board.Babylon.GetOptionsBabylon;
import com.jtriemstra.wonders.api.model.phases.AgePhase;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, TestBase.TestStateConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostTurnActionTests extends TestBase {
	
	// TODO: this is currently using default actions - worth mocking out "no actions"?
	/*@Test
	public void when_finishing_turn_with_no_actions_all_options_for_next_turn() {
		setupTest();
		Player p2 = gameWithThreePlayers.getPlayer("test2");
		Player p3 = gameWithThreePlayers.getPlayer("test3");
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("options", testPlayer.getNextAction().toString());
		Assertions.assertEquals("options", p2.getNextAction().toString());
		Assertions.assertEquals("options", p3.getNextAction().toString());
	}*/

	@Test
	public void when_finishing_turn_with_default_actions_all_options_for_next_turn() {
		setupTest();
		IPlayer p2 = gameWithThreePlayers.getPlayer("test2");
		IPlayer p3 = gameWithThreePlayers.getPlayer("test3");
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("options", testPlayer.getNextAction().toString());
		Assertions.assertEquals("options", p2.getNextAction().toString());
		Assertions.assertEquals("options", p3.getNextAction().toString());
	}

	@Test
	public void when_finishing_turn_with_defaults_and_hali_action_wait_for_hali_options() {
		setupTest();
		IPlayer p2 = gameWithThreePlayers.getPlayer("test2");
		IPlayer p3 = gameWithThreePlayers.getPlayer("test3");
		
		gameWithThreePlayers.getFlow().addPostTurnAction(testPlayer, new GetOptionsFromDiscard(), (p, g) -> {return p instanceof AgePhase;});
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("options", testPlayer.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(testPlayer.getNextAction().getByName("options") instanceof GetOptionsFromDiscard);
	}

	// TODO: this is currently using default actions - worth mocking out "no actions"?
	/*@Test
	public void when_finishing_turn_with_only_hali_action_wait_for_hali_options() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(p1, new GetOptionsFromDiscard());
	
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsFromDiscard);
	}*/


	@Test
	public void when_finishing_normal_turn_with_defaults_and_babylon_action_move_to_next_turn_options() {
		setupTest();
		IPlayer p2 = gameWithThreePlayers.getPlayer("test2");
		IPlayer p3 = gameWithThreePlayers.getPlayer("test3");
		
		Babylon b = new Babylon(false);
		gameWithThreePlayers.getFlow().addPostTurnAction(testPlayer, b.new GetOptionsBabylon(), (p,g) -> true);

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("options", testPlayer.getNextAction().toString());
		Assertions.assertTrue(testPlayer.getNextAction().getByName("options") instanceof GetOptionsBabylon);
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
		testPlayer.getNextAction().getByName("options").execute(new OptionsRequest(), testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("wait", testPlayer.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
	}
	
}
