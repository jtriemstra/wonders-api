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
import com.jtriemstra.wonders.api.model.action.GetOptionsFromDiscard;
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.leaders.Alexander;
import com.jtriemstra.wonders.api.model.card.leaders.Amytis;
import com.jtriemstra.wonders.api.model.card.leaders.Archimedes;
import com.jtriemstra.wonders.api.model.card.leaders.Aristotle;
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
import com.jtriemstra.wonders.api.model.card.leaders.Nero;
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

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Babylon-A;Rhodes-A"})
@Import(TestBase.TestConfig.class)
public class LeaderCardTests extends TestBase {
		
	private final static int DEFAULT_COINS=3;
		
	@Test
	public void when_playing_alexander_one_army_vp_becomes_two() {
		Card c = new Alexander();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		fakeVictoryTokens(p1, 1);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.ARMY));
	}

	@Test
	public void when_playing_alexander_three_army_vp_becomes_four() {
		Card c = new Alexander();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		fakeVictoryTokens(p1, 2);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(4, p1.getFinalVictoryPoints().get(VictoryPointType.ARMY));
	}
	
	@Test
	public void when_playing_alexander_two_army_vp_becomes_four() {
		Card c = new Alexander();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		fakeVictoryTokens(p1, 1);
		fakeVictoryTokens(p1, 1);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(4, p1.getFinalVictoryPoints().get(VictoryPointType.ARMY));
	}
	
	@Test
	public void when_playing_amytis_get_one_point_for_stage() {
		Card c = new Amytis();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		fakeBuildingStage(p1, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_amytis_get_two_point_for_two_stages() {
		Card c = new Amytis();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		fakeBuildingStage(p1, g);
		fakeBuildingStage(p1, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	//TODO: flesh this out in tests for play rules?
	@Test
	public void when_playing_archimedes_can_play_science_card_free() {
		Card c = new Archimedes();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		CardPlayable cp = p1.canPlay(new Apothecary(3,1), Mockito.mock(Player.class), Mockito.mock(Player.class));
		
		Assertions.assertTrue(cp.getStatus() == Status.OK);
	}

	@Test
	public void when_playing_aristotle_get_ten_points_for_set() {
		Card c = new Aristotle();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		fakePreviousCard(p1, new Apothecary(3,1), g);
		fakePreviousCard(p1, new Scriptorium(3,1), g);
		fakePreviousCard(p1, new Workshop(3,1), g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(13, p1.getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}

	@Test
	public void when_playing_caesar_get_two_shields() {
		Card c = new Caesar();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(2, p1.getArmies());
	}

	@Test
	public void when_playing_cleopatra_get_five_points() {
		Card c = new Cleopatra();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(5, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_croesus_get_six_coins() {
		Card c = new Croesus();
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpCardToPlayWithAction(p1, c, g);
		
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
		
		Assertions.assertEquals(DEFAULT_COINS - c.getCoinCost() + 6, p1.getCoins());
	}

	@Test
	public void when_playing_euclid_get_science_card() {
		Card c = new Euclid();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getScienceProviders().size());
		Assertions.assertEquals(ScienceType.COMPASS, p1.getScienceProviders().get(0).getScience().getScienceOptions()[0]);
	}

	@Test
	public void when_playing_hammurabi_can_play_victory_card_for_free() {
		Card c = new Hammurabi();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		CardPlayable cp = p1.canPlay(new Baths(3,1), Mockito.mock(Player.class), Mockito.mock(Player.class));
		
		Assertions.assertTrue(cp.getStatus() == Status.OK);
	}

	@Test
	public void when_playing_hannibal_get_one_shield() {
		Card c = new Hannibal();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getArmies());
	}

	@Test
	public void when_playing_hatshepsut_get_coin_after_trade() {
		Card c = new Hatshepsut();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		setUpNeighbors(g, p1);
		
		Assertions.assertEquals(DEFAULT_COINS, p1.getCoins());
		
		replicatePlayingCard(p1, c, g);
		
		Card c1 = new GuardTower(3,1);
		setUpCardToPlayWithAction(p1, c1, g);
		replicatePlayingCardWithAction(p1, c1, g);
		
		NonPlayerAction a = g.new ResolveCommerceAction();
		a.execute(g);
		
		Assertions.assertEquals(2, p1.getCoins());
	}

	@Test
	public void when_playing_hiram_with_no_guild_get_no_points() {
		Card c = new Hiram();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(0, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_hiram_with_guild_get_two_points() {
		Card c = new Hiram();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);

		fakePreviousCard(p1, new TradersGuild(3,3), g);
		
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
	@Test
	public void when_playing_hypatia_with_green_get_one_point() {
		Card c = new Hypatia();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);

		fakePreviousCard(p1, new Apothecary(3,1), g);
		
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_justinian_get_three_points_for_set() {
		Card c = new Justinian();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, new Apothecary(3,1), g);
		fakePreviousCard(p1, new Altar(3,1), g);
		fakePreviousCard(p1, new GuardTower(3,1), g);
		
		Assertions.assertEquals(3, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_leonidas_can_play_red_card_free() {
		Card c = new Leonidas();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		CardPlayable cp = p1.canPlay(new GuardTower(3,1), Mockito.mock(Player.class), Mockito.mock(Player.class));
		
		Assertions.assertTrue(cp.getStatus() == Status.OK);
	}

	@Test
	public void when_playing_maecenas() {
		Card c = new Maecenas();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		Assertions.assertEquals(DEFAULT_COINS, p1.getCoins());
		
		replicatePlayingCard(p1, c, g);

		Card c1 = new Midas();
		setUpCardToPlayWithAction(p1, c1, g);
		replicatePlayingCardWithAction(p1, c1, g);
		
		NonPlayerAction a = g.new ResolveCommerceAction();
		a.execute(g);
		
		Assertions.assertEquals(DEFAULT_COINS, p1.getCoins());
	}

	@Test
	public void when_playing_midas_three_coins_gets_one_point() {
		Card c = new Midas();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_nebuchadnezzar_with_blue_get_one_point() {
		Card c = new Nebuchadnezzar();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, new Altar(3,1), g);
		
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.VICTORY));
	}

	@Test
	public void when_playing_nefertiti() {
		Card c = new Nefertiti();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(4, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_nero_get_two_coins_per_victory() {
		Card c = new Nero();
		Game g = setUpFinalTurnGame();
		Player p1 = setUpPlayerWithCard(c, g);
		setUpNeighbors(g, p1);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, new GuardTower(3,1), g);
		
		NonPlayerAction a = g.new ResolveConflictAction();
		a.execute(g);
		
		Assertions.assertEquals(DEFAULT_COINS + 2 + 2, p1.getCoins());
	}

	@Test
	public void when_playing_pericles_with_red_card_get_two_points() {
		Card c = new Pericles();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, new GuardTower(3,1), g);
		
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_phidias_with_brown_card_get_point() {
		Card c = new Phidias();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, new StonePit(3,1), g);
		
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_plato_get_points_for_full_set_of_colors() {
		Card c = new Plato();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		fakePreviousCard(p1, new StonePit(3,1), g);
		fakePreviousCard(p1, new Loom(3,1), g);
		fakePreviousCard(p1, new Altar(3,1), g);
		fakePreviousCard(p1, new Scriptorium(3,1), g);
		fakePreviousCard(p1, new GuardTower(3,1), g);
		fakePreviousCard(p1, new Caravansery(3,2), g);
		fakePreviousCard(p1, new TradersGuild(3,3), g);
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(7, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_praxiteles_with_gray_get_two_points() {
		Card c = new Praxiteles();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, new Loom(3,1), g);
		
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_ptolemy_get_tablet() {
		Card c = new Ptolemy();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getScienceProviders().size());
		Assertions.assertEquals(ScienceType.TABLET, p1.getScienceProviders().get(0).getScience().getScienceOptions()[0]);		
	}

	@Test
	public void when_playing_pythagoras_get_gear() {
		Card c = new Pythagoras();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);

		Assertions.assertEquals(1, p1.getScienceProviders().size());
		Assertions.assertEquals(ScienceType.GEAR, p1.getScienceProviders().get(0).getScience().getScienceOptions()[0]);
	}

	@Test
	public void when_playing_ramses_can_play_guild_for_free() {
		Card c = new Ramses();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Card c1 = new TradersGuild(3,3);
		setUpCardToPlayWithAction(p1, c1, g);
		replicatePlayingCardWithAction(p1, c1, g);
		
		NonPlayerAction a = g.new PlayCardsAction();
		a.execute(g);
		
		Assertions.assertTrue(p1.getCardsOfTypeFromBoard(GuildCard.class).stream().anyMatch(c2 -> c2.getName().equals("Traders Guild")));
	}

	@Test
	public void when_playing_sappho() {
		Card c = new Sappho();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);

		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_solomon() {
		Card c = new Solomon();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		setUpNeighbors(g, p1);
		
		setUpCardToPlayWithAction(p1, c, g);
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
		
		Assertions.assertTrue(p1.getNextAction().getByName("options") instanceof GetOptionsFromDiscard);
		
	}

	@Test
	public void when_playing_varro_with_yellow_card_get_one_point() {
		Card c = new Varro();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, new Haven(3,1), g);
		
		Assertions.assertEquals(1, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}

	@Test
	public void when_playing_vitruvius_get_two_coins_for_chain() {
		Card c = new Vitruvius();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		fakePreviousCard(p1, new Apothecary(3,1), g);
		
		Card c1 = new Stables(3,2);
		setUpCardToPlayWithAction(p1, c1, g);
		replicatePlayingCardWithAction(p1, c1, g);
		
		Assertions.assertEquals(DEFAULT_COINS + 2, p1.getCoins());
	}

	@Test
	public void when_playing_xenophon_get_two_coins_for_yellow_card() {
		Card c = new Xenophon();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);
		
		Card c1 = new EastTradingPost(3,1);
		setUpCardToPlayWithAction(p1, c1, g);
		replicatePlayingCardWithAction(p1, c1, g);
		
		Assertions.assertEquals(DEFAULT_COINS + 2, p1.getCoins());
	}

	@Test
	public void when_playing_zenobia() {
		Card c = new Zenobia();
		Game g = setUpGame();
		Player p1 = setUpPlayerWithCard(c, g);
		
		replicatePlayingCard(p1, c, g);

		Assertions.assertEquals(3, p1.getFinalVictoryPoints().get(VictoryPointType.LEADER));
	}
	
}
