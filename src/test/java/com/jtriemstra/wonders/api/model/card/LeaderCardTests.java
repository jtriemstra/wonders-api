package com.jtriemstra.wonders.api.model.card;

import java.util.ArrayList;

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

import com.jtriemstra.wonders.api.LeadersTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.GetOptionsFromDiscard;
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.action.PlayCardsAction;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceLeadersDecorator;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.leaders.Alexander;
import com.jtriemstra.wonders.api.model.card.leaders.Amytis;
import com.jtriemstra.wonders.api.model.card.leaders.Archimedes;
import com.jtriemstra.wonders.api.model.card.leaders.Aristotle;
import com.jtriemstra.wonders.api.model.card.leaders.Bilkis;
import com.jtriemstra.wonders.api.model.card.leaders.Caesar;
import com.jtriemstra.wonders.api.model.card.leaders.Cleopatra;
import com.jtriemstra.wonders.api.model.card.leaders.Croesus;
import com.jtriemstra.wonders.api.model.card.leaders.Euclid;
import com.jtriemstra.wonders.api.model.card.leaders.Hammurabi;
import com.jtriemstra.wonders.api.model.card.leaders.Hannibal;
import com.jtriemstra.wonders.api.model.card.leaders.Hatshepsut;
import com.jtriemstra.wonders.api.model.card.leaders.Hiram;
import com.jtriemstra.wonders.api.model.card.leaders.Hypatia;
import com.jtriemstra.wonders.api.model.card.leaders.Justinian;
import com.jtriemstra.wonders.api.model.card.leaders.Leonidas;
import com.jtriemstra.wonders.api.model.card.leaders.Maecenas;
import com.jtriemstra.wonders.api.model.card.leaders.Midas;
import com.jtriemstra.wonders.api.model.card.leaders.Nebuchadnezzar;
import com.jtriemstra.wonders.api.model.card.leaders.Nefertiti;
import com.jtriemstra.wonders.api.model.card.leaders.Pericles;
import com.jtriemstra.wonders.api.model.card.leaders.Phidias;
import com.jtriemstra.wonders.api.model.card.leaders.Plato;
import com.jtriemstra.wonders.api.model.card.leaders.Praxiteles;
import com.jtriemstra.wonders.api.model.card.leaders.Ptolemy;
import com.jtriemstra.wonders.api.model.card.leaders.Pythagoras;
import com.jtriemstra.wonders.api.model.card.leaders.Ramses;
import com.jtriemstra.wonders.api.model.card.leaders.Sappho;
import com.jtriemstra.wonders.api.model.card.leaders.Solomon;
import com.jtriemstra.wonders.api.model.card.leaders.Varro;
import com.jtriemstra.wonders.api.model.card.leaders.Vitruvius;
import com.jtriemstra.wonders.api.model.card.leaders.Xenophon;
import com.jtriemstra.wonders.api.model.card.leaders.Zenobia;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;
import com.jtriemstra.wonders.api.model.leaders.PlayerLeaders;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.notifications.NotificationService;
import com.jtriemstra.wonders.api.state.StateService;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Babylon-A;Rhodes-A"})
@Import({TestBase.TestConfig.class, LeadersTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LeaderCardTests extends TestBase {

	protected final static int DEFAULT_COINS=3;
		
	@Test
	public void when_playing_alexander_one_army_vp_becomes_two() {
		setupTest(new Alexander());
		
		fakeVictoryTokens(testPlayer, 1);
		
		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.ARMY));
	}

	@Test
	public void when_playing_alexander_three_army_vp_becomes_four() {
		setupTest(new Alexander());
		
		fakeVictoryTokens(testPlayer, 2);
		
		Assertions.assertEquals(4, testPlayer.getFinalVictoryPoints().get(VictoryPointType.ARMY));
	}
	
	@Test
	public void when_playing_alexander_two_army_vp_becomes_four() {
		setupTest(new Alexander());
		
		fakeVictoryTokens(testPlayer, 1);
		fakeVictoryTokens(testPlayer, 1);
		
		Assertions.assertEquals(4, testPlayer.getFinalVictoryPoints().get(VictoryPointType.ARMY));
	}
	
	@Test
	public void when_playing_amytis_get_two_point_for_stage() {
		setupTest(new Amytis());
				
		fakeBuildingStage(testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_amytis_get_four_point_for_two_stages() {
		setupTest(new Amytis());
		
		fakeBuildingStage(testPlayer, gameWithThreePlayers);
		fakeBuildingStage(testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals(4, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	//TODO: flesh this out in tests for play rules?
	@Test
	public void when_playing_archimedes_can_play_science_card_free() {
		setupTest(new Archimedes());
		
		PlayableBuildableResult result = testPlayer.canPlay(new Apothecary(3,1), Mockito.mock(Player.class), Mockito.mock(Player.class));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		
		Assertions.assertTrue(cp.getStatus() == Status.OK);
	}
	
	@Test
	public void when_playing_aristotle_get_ten_points_for_set() {
		setupTest(new Aristotle(), new Apothecary(3,1), new Scriptorium(3,1), new Workshop(3,1));
		
		Assertions.assertEquals(13, testPlayer.getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}
	
	@Test
	public void when_playing_caesar_get_two_shields() {
		setupTest(new Caesar());
		
		Assertions.assertEquals(2, testPlayer.getArmyFacade().getArmies());
	}
	
	@Test
	public void when_playing_cleopatra_get_five_points() {
		setupTest(new Cleopatra());
		
		Assertions.assertEquals(5, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_croesus_get_six_coins() {
		Card testCard = new Croesus();
		setUpTestByActionIgnoringCosts(testCard);
		fakePlayingCardWithAction(testCard);
		
		// Note this is ignoring costs, including the coin cost of the card
		Assertions.assertEquals(DEFAULT_COINS /*- testCard.getCoinCost()*/ + 6, testPlayer.getCoins());
	}
	
	@Test
	public void when_playing_euclid_get_science_card() {
		setupTest(new Euclid());
		
		Assertions.assertEquals(1, testPlayer.getScienceProviders().size());
		Assertions.assertEquals(ScienceType.COMPASS, testPlayer.getScienceProviders().get(0).getScience().getScienceOptions()[0]);
	}
	
	@Test
	public void when_playing_hammurabi_can_play_victory_card_for_free() {
		setupTest(new Hammurabi());
		
		PlayableBuildableResult result = testPlayer.canPlay(new Baths(3,1), Mockito.mock(Player.class), Mockito.mock(Player.class));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		
		Assertions.assertTrue(cp.getStatus() == Status.OK);
	}
	
	@Test
	public void when_playing_hannibal_get_one_shield() {
		setupTest(new Hannibal());
		
		Assertions.assertEquals(1, testPlayer.getArmyFacade().getArmies());
	}
	
	@Test
	public void when_playing_hatshepsut_get_coin_after_trade() {
		Card testCard = new GuardTower(3,1);
		setUpTestByActionWithCosts(testCard, new Hatshepsut());
		
		Assertions.assertEquals(DEFAULT_COINS, testPlayer.getCoins());
		
		//TODO: somewhat fragile, relies on the board configuration so neighbor has brick
		fakePlayingCardWithAction(testCard);
		
		testPlayer.doScheduledAction();
		
		Assertions.assertEquals(2, testPlayer.getCoins());
	}
	
	@Test
	public void when_playing_hiram_with_no_guild_get_no_points() {
		setupTest(new Hiram());
		
		Assertions.assertEquals(0, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_hiram_with_guild_get_two_points() {
		setupTest(new TradersGuild(3,3), new Hiram());
				
		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_hypatia_with_green_get_one_point() {
		setupTest(new Hypatia(), new Apothecary(3,1));
			
		Assertions.assertEquals(1, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_justinian_get_three_points_for_set() {
		setupTest(new Justinian(), new Apothecary(3,1),  new Altar(3,1), new GuardTower(3,1));
		
		Assertions.assertEquals(3, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_leonidas_can_play_red_card_free() {
		setupTest(new Leonidas());
		
		PlayableBuildableResult result = testPlayer.canPlay(new GuardTower(3,1), Mockito.mock(Player.class), Mockito.mock(Player.class));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		
		Assertions.assertTrue(cp.getStatus() == Status.OK);
	}
	
	@Test
	public void when_playing_maecenas() {
		Card c1 = new Midas();
		setUpTestByActionWithCosts(c1, new Maecenas());
		
		Assertions.assertEquals(DEFAULT_COINS, testPlayer.getCoins());
		
		fakePlayingCardWithAction(c1);
		
		testPlayer.doScheduledAction();
		
		Assertions.assertEquals(DEFAULT_COINS, testPlayer.getCoins());
	}
	
	@Test
	public void when_playing_midas_three_coins_gets_one_point() {
		setupTest(new Midas());
				
		Assertions.assertEquals(1, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_nebuchadnezzar_with_blue_get_one_point() {
		setupTest(new Altar(3,1), new Nebuchadnezzar());
		
		Assertions.assertEquals(1, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.VICTORY));
	}
	
	@Test
	public void when_playing_nefertiti_get_4_points() {
		setupTest(new Nefertiti());
		
		Assertions.assertEquals(4, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_pericles_with_red_card_get_two_points() {
		setupTest(new GuardTower(3,1), new Pericles());
		
		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_phidias_with_brown_card_get_point() {
		setupTest(new StonePit(3,1), new Phidias());
		
		Assertions.assertEquals(1, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_plato_get_points_for_full_set_of_colors() {
		setupTest(new StonePit(3,1), new Loom(3,1), new Altar(3,1), new Scriptorium(3,1), new GuardTower(3,1), new Caravansery(3,2), new TradersGuild(3,3), new Plato());
		
		Assertions.assertEquals(7, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_praxiteles_with_gray_get_two_points() {
		setupTest(new Loom(3,1), new Praxiteles());

		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_ptolemy_get_tablet() {
		setupTest(new Ptolemy());
		
		Assertions.assertEquals(1, testPlayer.getScienceProviders().size());
		Assertions.assertEquals(ScienceType.TABLET, testPlayer.getScienceProviders().get(0).getScience().getScienceOptions()[0]);		
	}
	
	@Test
	public void when_playing_pythagoras_get_gear() {
		setupTest(new Pythagoras());
		
		Assertions.assertEquals(1, testPlayer.getScienceProviders().size());
		Assertions.assertEquals(ScienceType.GEAR, testPlayer.getScienceProviders().get(0).getScience().getScienceOptions()[0]);
	}
	
	@Test
	public void when_playing_ramses_can_play_guild_for_free() {
		Card testCard = new TradersGuild(3,3);
		setUpTestByActionWithCosts(testCard, new Ramses());
		fakePlayingCardWithAction(testCard);
		
		NonPlayerAction a = new PlayCardsAction();
		a.execute(gameWithThreePlayers);
		
		Assertions.assertTrue(testPlayer.getCardsOfTypeFromBoard(GuildCard.class).stream().anyMatch(c2 -> c2.getName().equals("Traders Guild")));
	}
	
	@Test
	public void when_playing_sappho() {
		setupTest(new Sappho());
		
		Assertions.assertEquals(2, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_solomon() {
		Card testCard = new Solomon();
		setUpTestByActionWithCosts(testCard);
		fakePlayingCardWithAction(testCard);
				
		Assertions.assertTrue(testPlayer.getNextAction().getByName("options") instanceof GetOptionsFromDiscard);
		
	}
	
	@Test
	public void when_playing_varro_with_yellow_card_get_one_point() {
		setupTest(new Haven(3,1), new Varro());
		
		Assertions.assertEquals(1, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_vitruvius_get_two_coins_for_chain() {
		Card testCard = new Stables(3,2);
		setUpTestByActionWithCosts(testCard, new Apothecary(3,1), new Vitruvius());
		
		fakePlayingCardWithAction(testCard);
		
		Assertions.assertEquals(DEFAULT_COINS + 2, testPlayer.getCoins());
	}
	
	@Test
	public void when_playing_xenophon_get_two_coins_for_yellow_card() {
		Card testCard = new EastTradingPost(3,1);
		setUpTestByActionWithCosts(testCard, new Xenophon());
		
		fakePlayingCardWithAction(testCard);
		
		Assertions.assertEquals(DEFAULT_COINS + 2, testPlayer.getCoins());
	}
	
	@Test
	public void when_playing_zenobia() {
		setupTest(new Zenobia());
			
		Assertions.assertEquals(3, testPlayer.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_bilkis_can_purchase_from_bank() {
		Card testCard = new Bilkis();
		setUpTestByActionIgnoringCosts(testCard);
		testPlayer.gainCoins(3);
		//fakePlayingCard(gameWithThreePlayers.getPlayer("test2"), new Glassworks(3,1), gameWithThreePlayers);
		fakePlayingCardWithAction(testCard);
		
		Card c1 = new Workshop(3, 1);
		
		PlayableBuildableResult result = testPlayer.canPlay(c1, gameWithThreePlayers.getLeftOf(testPlayer), gameWithThreePlayers.getRightOf(testPlayer));
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		
		Assertions.assertEquals(1, cp.getCostOptions().size());
		Assertions.assertEquals(1, cp.getCostOptions().get(0).get("Bank"));
	}

}
