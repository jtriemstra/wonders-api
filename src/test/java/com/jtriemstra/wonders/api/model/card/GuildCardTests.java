package com.jtriemstra.wonders.api.model.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.LambdaVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class GuildCardTests {

	@Autowired
	PlayerFactory playerFactory;
	
	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
	
	@Test
	public void when_playing_traders_guild_add_correct_vp_provider() {
		Card c = new TradersGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
	@Test
	public void when_playing_scientists_guild_add_post_game_action() {
		Card c = new ScientistsGuild(3,3);
		Game g = Mockito.spy(gameFactory.createGame("test1", boardFactory));
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(0, p1.getVictoryPoints().size());
		Mockito.verify(g, Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsScience.class));
	}

	@Test
	public void when_playing_philosophers_guild_add_correct_vp_provider() {
		Card c = new PhilosophersGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_spies_guild_add_correct_vp_provider() {
		Card c = new SpiesGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_shipowners_guild_add_correct_vp_provider() {
		Card c = new ShipownersGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(3, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
		Assertions.assertTrue(p1.getVictoryPoints().get(1) instanceof CardVPProvider);
		Assertions.assertTrue(p1.getVictoryPoints().get(2) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_craftsmens_guild_add_correct_vp_provider() {
		Card c = new CraftsmensGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_workers_guild_add_correct_vp_provider() {
		Card c = new WorkersGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}

	@Test
	public void when_playing_builders_guild_add_correct_vp_provider() {
		Card c = new BuildersGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof StageVPProvider);
	}

	@Test
	public void when_playing_strategists_guild_add_correct_vp_provider() {
		Card c = new StrategistsGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof LambdaVPProvider);
	}

	@Test
	public void when_playing_magistrates_guild_add_correct_vp_provider() {
		Card c = new MagistratesGuild(3,3);
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerFactory.createPlayer("test1"));
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof CardVPProvider);
	}
	
	@TestConfiguration
	static class TestConfig {
		
		@Bean
		@Primary
		@Scope("prototype")
		PlayerList createMockPlayerList() {
			PlayerList mock = Mockito.mock(PlayerList.class);
			Player left = Mockito.mock(Player.class);
			Player right = Mockito.mock(Player.class);
			
			Mockito.when(left.getName()).thenReturn("test-left");
			Mockito.when(right.getName()).thenReturn("test-right");
			Mockito.doReturn(left).when(mock).getLeftOf(Mockito.any());
			Mockito.doReturn(right).when(mock).getRightOf(Mockito.any());
			
			return mock;
		}
	}
}
