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
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.Babylon;
import com.jtriemstra.wonders.api.model.board.Babylon.GetOptionsBabylon;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostTurnActionTests extends TestBase {
		
	@Test
	public void when_finishing_turn_with_no_actions_all_options_for_next_turn() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("options", p2.getNextAction().toString());
		Assertions.assertEquals("options", p3.getNextAction().toString());
	}

	@Test
	public void when_finishing_turn_with_default_actions_all_options_for_next_turn() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("options", p2.getNextAction().toString());
		Assertions.assertEquals("options", p3.getNextAction().toString());
	}

	@Test
	public void when_finishing_turn_with_defaults_and_hali_action_wait_for_hali_options() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		g.addPostTurnAction(p1, new GetOptionsFromDiscard());
		
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsFromDiscard);
	}

	@Test
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
	}

	@Test
	public void when_finishing_final_turn_with_defaults_and_hali_action_wait_for_hali_options() {
		Game g = setUpGame(finalTurnGameFactory);
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		g.addPostTurnAction(p1, new GetOptionsFromDiscard());

		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsFromDiscard);
	}

	@Test
	public void when_finishing_final_age_turn_with_defaults_and_hali_action_wait_for_hali_options() {
		Game g = setUpGame(finalTurnGameFactory);
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
				
		g.addPostTurnAction(p1, new GetOptionsFromDiscard());

		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsFromDiscard);
	}
	
	@Test
	public void when_finishing_final_turn_with_defaults_and_babylon_action_wait_for_babylon_options() {
		Game g = setUpGame(finalTurnGameFactory);
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		Babylon b = new Babylon(false);
		g.addPostTurnAction(p1, b.new GetOptionsBabylon());

		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsBabylon);
		
		
	}

	@Test
	public void when_finishing_normal_turn_with_defaults_and_babylon_action_move_to_next_turn_options() {
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		g.addPostTurnAction(null, g.new PlayCardsAction());
		g.addPostTurnAction(null, g.new ResolveCommerceAction());
		g.addPostTurnAction(null, g.new DiscardFinalCardAction());
		g.addPostTurnAction(null, g.new ResolveConflictAction());
		
		Babylon b = new Babylon(false);
		g.addPostTurnAction(p1, b.new GetOptionsBabylon());

		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsBabylon);
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
		p1.getNextAction().getByName("options").execute(new OptionsRequest(), p1, g);
		
		Assertions.assertEquals("wait", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
	}
	
}
