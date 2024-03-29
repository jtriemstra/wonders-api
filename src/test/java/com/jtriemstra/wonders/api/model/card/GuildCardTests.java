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

import com.jtriemstra.wonders.api.SpyGameFlowTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.action.Play;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.LambdaVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, SpyGameFlowTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GuildCardTests {

	protected void setupTest(Card testCard) {
		testPlayer = gameWithThreePlayers.getPlayer("test1");
		
		fakePlayingCard(testPlayer, testCard, gameWithThreePlayers);
	}

	protected void fakePlayingCard(IPlayer testPlayer, Card c, Game g) {
		testPlayer.receiveCard(c);
		CardPlayable cp = new CardPlayable(c, Status.OK, new ArrayList<>(), 0);
		ArrayList<CardPlayable> options = new ArrayList<>();
		options.add(cp);
		Play x = new Play(options, cardName -> testPlayer.removeCardFromHand(cardName));
		testPlayer.scheduleTurnAction(notifications -> x.doPlay(testPlayer, g, c.getName(), notifications, 0));
		testPlayer.doScheduledAction();
	}
	
	@Autowired @Qualifier("gameWithThreePlayers")
	Game gameWithThreePlayers;
	
	IPlayer testPlayer;
	
	
	@Test
	public void when_playing_traders_guild_add_correct_vp_provider() {
		setupTest(new TradersGuild(3,3));
				
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
	@Test
	public void when_playing_scientists_guild_add_post_game_action() {
		setupTest(new ScientistsGuild(3,3));
		
		Assertions.assertEquals(0, testPlayer.getVictoryPoints().size());
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsScience.class), Mockito.any(Class.class));
	}
	
	@Test
	public void when_playing_philosophers_guild_add_correct_vp_provider() {
		setupTest(new PhilosophersGuild(3,3));
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
	@Test
	public void when_playing_spies_guild_add_correct_vp_provider() {
		setupTest(new SpiesGuild(3,3));
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
	@Test
	public void when_playing_shipowners_guild_add_correct_vp_provider() {
		setupTest(new ShipownersGuild(3,3));
		
		Assertions.assertEquals(3, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(1) instanceof CardVPProvider);
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(2) instanceof CardVPProvider);
	}
	
	@Test
	public void when_playing_craftsmens_guild_add_correct_vp_provider() {
		setupTest(new CraftsmensGuild(3,3));
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
	@Test
	public void when_playing_workers_guild_add_correct_vp_provider() {
		setupTest(new WorkersGuild(3,3));
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
	@Test
	public void when_playing_builders_guild_add_correct_vp_provider() {
		setupTest(new BuildersGuild(3,3));
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof StageVPProvider);
	}
	
	@Test
	public void when_playing_strategists_guild_add_correct_vp_provider() {
		setupTest(new StrategistsGuild(3,3));
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof LambdaVPProvider);
	}
	
	@Test
	public void when_playing_magistrates_guild_add_correct_vp_provider() {
		setupTest(new MagistratesGuild(3,3));
		
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Assertions.assertTrue(testPlayer.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
}
