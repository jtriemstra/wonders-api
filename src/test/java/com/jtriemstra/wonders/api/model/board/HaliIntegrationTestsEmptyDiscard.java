package com.jtriemstra.wonders.api.model.board;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.BuildRequest;
import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.dto.request.WaitRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.Build;
import com.jtriemstra.wonders.api.model.action.CardRemoveStrategy;
import com.jtriemstra.wonders.api.model.action.Play;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.StonePit;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Halikarnassos-B;Halikarnassos-B;Halikarnassos-B"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class HaliIntegrationTestsEmptyDiscard extends BoardTestBase {
	
	@Test
	public void when_notify_waiting_for_turn_return_false() {
		setupTest();
		
		IPlayer p1 = testPlayer;
		IPlayer p2 = gameWithThreePlayers.getPlayer("test2");
		IPlayer p3 = gameWithThreePlayers.getPlayer("test3");
		
		mockActions(p1);
		mockActions(p2);
		mockActions(p3);
				
		BuildRequest br = new BuildRequest();
		br.setCardName("Stone Pit");
		PlayRequest pr = new PlayRequest();
		pr.setCardName("Stone Pit");
		
		ActionResponse r1 = p1.doAction(br, gameWithThreePlayers);
		ActionResponse r2 = p2.doAction(pr, gameWithThreePlayers);
		ActionResponse r3 = p3.doAction(pr, gameWithThreePlayers);
		
		Assertions.assertEquals("wait", r1.getNextActions());
		Assertions.assertEquals("wait", r2.getNextActions());
		Assertions.assertEquals("wait", r3.getNextActions());
		
		p3.doAction(new WaitRequest(), gameWithThreePlayers);
		Assertions.assertEquals("options", p1.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
	}
	
	private void mockActions(IPlayer p) {
		Card c = new StonePit(3,1);
		CardPlayable cp = new CardPlayable(c, Status.OK, 0, 0, 0);
		List<CardPlayable> cards = new ArrayList<>();
		cards.add(cp);
		
		Halikarnassos hali = new Halikarnassos(false);
		Buildable buildable = new Buildable(hali.new B1(), Status.OK, 0, 0, 0);
		
		p.receiveCard(c);
		p.addNextAction(new WaitTurn());
		p.addNextAction(new Play(cards, Mockito.mock(CardRemoveStrategy.class)), new Build(buildable, Mockito.mock(CardRemoveStrategy.class)));
	}
	
}
