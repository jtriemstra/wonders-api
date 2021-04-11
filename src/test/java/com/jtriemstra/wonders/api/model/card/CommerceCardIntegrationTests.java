package com.jtriemstra.wonders.api.model.card;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.BaseAction;
import com.jtriemstra.wonders.api.model.action.Play;
import com.jtriemstra.wonders.api.model.action.Wait;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class CommerceCardIntegrationTests extends TestBase {
	
	@Test
	public void when_playing_arena_integration_test() {
		Card c = new Arena(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		
		Mockito.doReturn(3).when(p1).getNumberOfBuiltStages();
		
		int originalCoins = p1.getCoins();
		
		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof StageVPProvider);
		Assertions.assertEquals(originalCoins + 9, p1.getCoins());
	}
}
