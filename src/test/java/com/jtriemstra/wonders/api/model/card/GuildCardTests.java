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
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.LambdaVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class GuildCardTests extends TestBase {

	@Test
	public void when_playing_traders_guild_add_correct_vp_provider() {
		Card c = new TradersGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
	@Test
	public void when_playing_scientists_guild_add_post_game_action() {
		Card c = new ScientistsGuild(3,3);
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(0, p1.getVictoryPoints().size());
		Mockito.verify(g, Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsScience.class));
	}

	@Test
	public void when_playing_philosophers_guild_add_correct_vp_provider() {
		Card c = new PhilosophersGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_spies_guild_add_correct_vp_provider() {
		Card c = new SpiesGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_shipowners_guild_add_correct_vp_provider() {
		Card c = new ShipownersGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(3, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
		Assertions.assertTrue(p1.getVictoryPoints().get(1) instanceof CardVPProvider);
		Assertions.assertTrue(p1.getVictoryPoints().get(2) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_craftsmens_guild_add_correct_vp_provider() {
		Card c = new CraftsmensGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_workers_guild_add_correct_vp_provider() {
		Card c = new WorkersGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_builders_guild_add_correct_vp_provider() {
		Card c = new BuildersGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof StageVPProvider);
	}

	@Test
	public void when_playing_strategists_guild_add_correct_vp_provider() {
		Card c = new StrategistsGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof LambdaVPProvider);
	}

	@Test
	public void when_playing_magistrates_guild_add_correct_vp_provider() {
		Card c = new MagistratesGuild(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		setUpNeighbors(g, p1);
		
		fakePreviousCard(p1, c, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}	
}
