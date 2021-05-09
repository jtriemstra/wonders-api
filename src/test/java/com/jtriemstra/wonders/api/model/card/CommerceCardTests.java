package com.jtriemstra.wonders.api.model.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Giza-A;Ephesus-A"})
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
	
	@Test
	public void when_playing_tavern_get_five_coins() {
		Card c = new Tavern(4, 1);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);

		int originalCoins = p1.getCoins();

		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
		
		Assertions.assertEquals(originalCoins + 5, p1.getCoins());
	}
	
	@Test
	public void when_playing_marketplace_can_trade_tech_cheaper() {
		Card c = new Marketplace(3, 1);
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p1 = getPresetPlayer(g);
		setUpNeighborCards(g, "test2", new Glassworks(3,1));
		
		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		fakeFinishingTurn(g);
		
		Card c1 = new Workshop(3, 1);
		CardPlayable cp = p1.canPlay(c1, g.getLeftOf(p1), g.getRightOf(p1));
		
		Assertions.assertEquals(1, cp.getCost());

	}

	@Test
	public void when_playing_marketplace_cannot_trade_natural_cheaper() {
		Card c = new Marketplace(3, 1);
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p1 = getPresetPlayer(g);
		setUpNeighborCards(g, "test2", new ClayPool(3,1));
		
		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		fakeFinishingTurn(g);
		
		Card c1 = new GuardTower(3, 1);
		CardPlayable cp = p1.canPlay(c1, g.getLeftOf(p1), g.getRightOf(p1));
		
		Assertions.assertEquals(2, cp.getCost());

	}
	
	@Test
	public void when_playing_etp_can_trade_right_cheaper() {
		Card c = new EastTradingPost(3, 1);
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p1 = getPresetPlayer(g);
		setUpNeighborCards(g, g.getRightOf(p1).getName(), new ClayPool(3,1));
		
		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		fakeFinishingTurn(g);
		
		Card c1 = new GuardTower(3, 1);
		CardPlayable cp = p1.canPlay(c1, g.getLeftOf(p1), g.getRightOf(p1));
		
		Assertions.assertEquals(1, cp.getCost());
		Assertions.assertEquals(1, cp.getRightCost());
	}
	
	@Test
	public void when_playing_wtp_can_trade_left_cheaper() {
		Card c = new WestTradingPost(3, 1);
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p1 = getPresetPlayer(g);
		setUpNeighborCards(g, g.getLeftOf(p1).getName(), new ClayPool(3,1));
		
		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		fakeFinishingTurn(g);
		
		Card c1 = new GuardTower(3, 1);
		CardPlayable cp = p1.canPlay(c1, g.getLeftOf(p1), g.getRightOf(p1));
		
		Assertions.assertEquals(1, cp.getCost());
		Assertions.assertEquals(1, cp.getLeftCost());
	}

	@Test
	public void when_playing_lighthouse_with_one_yellow_card_get_coin_and_vp() {
		Card c = new Lighthouse(3,3);
		
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		fakePreviousCard(p1, new Forum(3,2), g);
		fakePreviousCard(p1, new StonePit(3,1), g);
		
		int originalCoins = p1.getCoins();

		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
				
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.COMMERCE));
		Assertions.assertEquals(originalCoins + 2, p1.getCoins());
		
	}

	@Test
	public void when_playing_chamber_with_one_gray_card_get_coin_and_vp() {
		Card c = new Lighthouse(3,3);
		
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		fakePreviousCard(p1, new Loom(3,1), g);
		fakePreviousCard(p1, new StonePit(3,1), g);
		
		int originalCoins = p1.getCoins();

		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
				
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.COMMERCE));
		Assertions.assertEquals(originalCoins + 1, p1.getCoins());
		
	}
}
