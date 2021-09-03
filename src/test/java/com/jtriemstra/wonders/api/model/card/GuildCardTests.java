package com.jtriemstra.wonders.api.model.card;

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
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.LambdaVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GuildCardTests {

	protected void setupTest(Card testCard) {
		testPlayer = gameWithThreePlayers.getPlayer("test1");
		
		fakePlayingCard(testPlayer, testCard, gameWithThreePlayers);
	}

	protected void fakePlayingCard(Player testPlayer, Card c, Game g) {
		testPlayer.receiveCard(c);
		testPlayer.scheduleCardToPlay(c);
		testPlayer.playScheduledCard(g);
	}
	
	@Autowired @Qualifier("gameWithThreePlayers")
	Game gameWithThreePlayers;
	
	Player testPlayer;
	
	
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
	
	@TestConfiguration
	public static class TestConfig {
		
		@Bean
		@Scope("prototype")
		@Primary
		public GameFlowFactory spyGameFlowFactory() {
			return phaseFactory -> Mockito.spy(new GameFlow(phaseFactory));
		}
		
	}
}
