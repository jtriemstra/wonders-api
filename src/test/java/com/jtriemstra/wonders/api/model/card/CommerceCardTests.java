package com.jtriemstra.wonders.api.model.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Giza-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, TestBase.TestStateConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommerceCardTests extends TestBase {

	@Test
	public void when_playing_haven_with_no_cards_get_no_coins_and_vp() {
		Card testCard = new Haven(3,3);
		setUpTestByActionIgnoringCosts(testCard);
		int originalCoins = testPlayer.getCoins();
		
		fakePlayingCardWithAction(testCard);
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
		Assertions.assertEquals(originalCoins, testPlayer.getCoins());
		
	}
	
	@Test
	public void when_playing_haven_with_two_brown_cards_get_two_coins_and_vp() {
		Card testCard = new Haven(3,3);
		setUpTestByActionIgnoringCosts(testCard, new StonePit(3,1), new TimberYard(3,1));
		int originalCoins = testPlayer.getCoins();
		
		fakePlayingCardWithAction(testCard);
				
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
		Assertions.assertEquals(originalCoins + 2, testPlayer.getCoins());
		
	}
	
	@Test
	public void when_playing_arena_with_no_stages_get_no_coins() {
		Card testCard = new Arena(3,3);
		setUpTestByActionIgnoringCosts(testCard);
		Mockito.doReturn(0).when(testPlayer).getNumberOfBuiltStages();
		int originalCoins = testPlayer.getCoins();
		
		fakePlayingCardWithAction(testCard);
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof StageVPProvider);
		Assertions.assertEquals(originalCoins, testPlayer.getCoins());
		
	}

	@Test
	public void when_playing_arena_with_three_stages_get_nine_coins() {
		Card testCard = new Arena(3,3);
		setUpTestByActionIgnoringCosts(testCard);
		Mockito.doReturn(3).when(testPlayer).getNumberOfBuiltStages();
		int originalCoins = testPlayer.getCoins();
		
		fakePlayingCardWithAction(testCard);
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof StageVPProvider);
		Assertions.assertEquals(originalCoins + 9, testPlayer.getCoins());
		
	}
	
	@Test
	public void when_playing_tavern_get_five_coins() {
		Card testCard = new Tavern(3,3);
		setUpTestByActionIgnoringCosts(testCard);
		int originalCoins = testPlayer.getCoins();
		
		fakePlayingCardWithAction(testCard);
		
		Assertions.assertEquals(originalCoins + 5, testPlayer.getCoins());
	}
	
	@Test
	public void when_playing_marketplace_can_trade_tech_cheaper() {
		Card testCard = new Marketplace(3,1);
		setUpTestByActionIgnoringCosts(testCard);
		fakePlayingCard(gameWithThreePlayers.getPlayer("test2"), new Glassworks(3,1), gameWithThreePlayers);
		fakePlayingCardWithAction(testCard);
		
		Card c1 = new Workshop(3, 1);
		
		PlayableBuildableResult result = testPlayer.canPlay(c1, gameWithThreePlayers.getLeftOf(testPlayer), gameWithThreePlayers.getRightOf(testPlayer));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		
		Assertions.assertEquals(1, cp.getCostOptions().size());
		Assertions.assertEquals(1, cp.getCostOptions().get(0).get("Right"));
	}

	@Test
	public void when_playing_marketplace_cannot_trade_natural_cheaper() {
		Card testCard = new Marketplace(3,1);
		setUpTestByActionIgnoringCosts(testCard);
		fakePlayingCard(gameWithThreePlayers.getPlayer("test2"), new ClayPool(3,1), gameWithThreePlayers);
		fakePlayingCardWithAction(testCard);
		
		Card c1 = new GuardTower(3, 1);
		
		PlayableBuildableResult result = testPlayer.canPlay(c1, gameWithThreePlayers.getLeftOf(testPlayer), gameWithThreePlayers.getRightOf(testPlayer));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		

		Assertions.assertEquals(1, cp.getCostOptions().size());
		Assertions.assertEquals(2, cp.getCostOptions().get(0).get("Right"));
	}
	
	@Test
	public void when_playing_etp_can_trade_right_cheaper() {
		Card c = new EastTradingPost(3, 1);
		setUpTestByActionIgnoringCosts(c);
		fakePlayingCard(gameWithThreePlayers.getRightOf(testPlayer), new ClayPool(3,1), gameWithThreePlayers);
		fakePlayingCardWithAction(c);
		
		Card c1 = new GuardTower(3, 1);
		PlayableBuildableResult result = testPlayer.canPlay(c1, gameWithThreePlayers.getLeftOf(testPlayer), gameWithThreePlayers.getRightOf(testPlayer));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		

		Assertions.assertEquals(1, cp.getCostOptions().size());
		Assertions.assertEquals(1, cp.getCostOptions().get(0).get("Right"));
	}
	
	@Test
	public void when_playing_wtp_can_trade_left_cheaper() {
		Card c = new WestTradingPost(3, 1);
		setUpTestByActionIgnoringCosts(c);
		fakePlayingCard(gameWithThreePlayers.getLeftOf(testPlayer), new ClayPool(3,1), gameWithThreePlayers);
		fakePlayingCardWithAction(c);
		
		Card c1 = new GuardTower(3, 1);
		PlayableBuildableResult result = testPlayer.canPlay(c1, gameWithThreePlayers.getLeftOf(testPlayer), gameWithThreePlayers.getRightOf(testPlayer));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		

		Assertions.assertEquals(1, cp.getCostOptions().size());
		Assertions.assertEquals(1, cp.getCostOptions().get(0).get("Left"));
	}

	@Test
	public void when_playing_lighthouse_with_one_yellow_card_get_coin_and_vp() {
		Card testCard = new Lighthouse(3,3);
		setUpTestByActionIgnoringCosts(testCard, new Forum(3,2), new StonePit(3,1));
		
		int originalCoins = testPlayer.getCoins();

		fakePlayingCardWithAction(testCard);
				
		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.COMMERCE));
		Assertions.assertEquals(originalCoins + 1, testPlayer.getCoins());
		
	}

	@Test
	public void when_playing_chamber_with_one_gray_card_get_coin_and_vp() {
		Card testCard = new ChamberOfCommerce(3,3);
		setUpTestByActionIgnoringCosts(testCard, new Loom(3,2), new StonePit(3,1));
		
		int originalCoins = testPlayer.getCoins();

		fakePlayingCardWithAction(testCard);
		
		testPlayer.doScheduledAction();
				
		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.COMMERCE));
		Assertions.assertEquals(originalCoins + 2, testPlayer.getCoins());
		
	}
}
