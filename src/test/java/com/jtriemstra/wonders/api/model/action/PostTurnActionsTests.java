package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Ages;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.Wait;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class PostTurnActionsTests extends TestBase {
		
	String dummyResults = "";

	@Test
	public void when_wait_for_turn_execute_actions_in_order() {
		dummyResults = "";
		Game g = setUpGame(gameFactory);
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		g.startNextPhase();
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		g.addPostTurnAction(null, new DummyPostTurnAction2());
		g.addPostTurnAction(null, new DummyPostTurnAction1());
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals("12", dummyResults);
	}
	
	@Test
	public void when_wait_for_turn_execute_postgame_actions_in_order() {
		dummyResults = "";
		Game g = setUpGame(finalAgeGameFactory);
		g.startNextPhase();
		//mock all waiting, since startNextPhase messed that up
		g.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		g.addPostGameAction(null, new DummyPostGameAction1());

		Player p = Mockito.mock(Player.class);
		
		WaitTurn w = new WaitTurn();
		w.execute(null, p, g);
		
		Assertions.assertEquals("game1", dummyResults);
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
