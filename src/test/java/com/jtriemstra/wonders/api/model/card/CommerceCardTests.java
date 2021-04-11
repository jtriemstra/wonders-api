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
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.BaseAction;
import com.jtriemstra.wonders.api.model.action.Play;
import com.jtriemstra.wonders.api.model.action.Wait;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class CommerceCardTests extends TestBase {

	@Test
	public void when_playing_haven_with_no_cards_get_no_coins_and_vp() {
		Card c = new Haven(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		int originalCoins = p1.getCoins();

		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
		Assertions.assertEquals(originalCoins, p1.getCoins());
		
	}
	
	@Test
	public void when_playing_haven_with_two_brown_cards_get_two_coins_and_vp() {
		Card c = new Haven(3,3);
		
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		fakePreviousCard(p1, new StonePit(3,1), g);
		fakePreviousCard(p1, new TimberYard(3,1), g);
		
		int originalCoins = p1.getCoins();

		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
				
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
		Assertions.assertEquals(originalCoins + 2, p1.getCoins());
		
	}
	
	@Test
	public void when_playing_arena_with_no_stages_get_no_coins() {
		Card c = new Arena(3,3);

		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		
		Mockito.doReturn(0).when(p1).getNumberOfBuiltStages();
		int originalCoins = p1.getCoins();

		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof StageVPProvider);
		Assertions.assertEquals(originalCoins, p1.getCoins());
		
	}

	@Test
	public void when_playing_arena_with_three_stages_get_nine_coins() {
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
