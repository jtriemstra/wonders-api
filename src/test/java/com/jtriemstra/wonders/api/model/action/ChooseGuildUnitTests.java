package com.jtriemstra.wonders.api.model.action;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.dto.request.ChooseGuildRequest;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Apothecary;
import com.jtriemstra.wonders.api.model.card.Arena;
import com.jtriemstra.wonders.api.model.card.Barracks;
import com.jtriemstra.wonders.api.model.card.BuildersGuild;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.ClayPit;
import com.jtriemstra.wonders.api.model.card.CraftsmensGuild;
import com.jtriemstra.wonders.api.model.card.Loom;
import com.jtriemstra.wonders.api.model.card.MagistratesGuild;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.PhilosophersGuild;
import com.jtriemstra.wonders.api.model.card.ScientistsGuild;
import com.jtriemstra.wonders.api.model.card.ShipownersGuild;
import com.jtriemstra.wonders.api.model.card.SpiesGuild;
import com.jtriemstra.wonders.api.model.card.StrategistsGuild;
import com.jtriemstra.wonders.api.model.card.TradersGuild;
import com.jtriemstra.wonders.api.model.card.WorkersGuild;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class ChooseGuildUnitTests {
	
	public ChooseGuildRequest getRequest(Card c) {
		ChooseGuildRequest r = new ChooseGuildRequest();
		r.setOptionName(c.getName());
		return r;
	}
		
	@Test
	public void when_choosing_magistrates_guild_then_get_points_from_neighbor() {
		Card guildCard = new MagistratesGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsOnBoard("test2", new Palace(3,3))
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}
	
	@Test
	public void when_choosing_traders_guild_then_get_points_from_neighbor() {
		Card guildCard = new TradersGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsOnBoard("test2", new Arena(3,3))
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}	
	
	@Test
	public void when_choosing_philosophers_guild_then_get_points_from_neighbor() {
		Card guildCard = new PhilosophersGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsOnBoard("test2", new Apothecary(1,3))
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}	
	
	@Test
	public void when_choosing_spies_guild_then_get_points_from_neighbor() {
		Card guildCard = new SpiesGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsOnBoard("test2", new Barracks(1,3))
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}	
	
	@Test
	public void when_choosing_craftsmens_guild_then_get_points_from_neighbor() {
		Card guildCard = new CraftsmensGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsOnBoard("test2", new Loom(1,3))
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(2, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}	
	
	@Test
	public void when_choosing_workers_guild_then_get_points_from_neighbor() {
		Card guildCard = new WorkersGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsOnBoard("test2", new ClayPit(1,3))
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}
	
	//TODO: tests for negative conditions, multiple card conditions, exception conditions (selected card not available)
	
	@Test
	public void when_choosing_shipowners_guild_then_get_points_from_self() {
		Card guildCard = new ShipownersGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerCardsOnBoard("test1", new ClayPit(1,3))
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}
	
	@Test
	public void when_choosing_scientists_guild_then_get_science_action() {
		Card guildCard = new ScientistsGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(0, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
		// TODO: how to test existence of post game actions?
	}
	
	@Test
	public void when_choosing_builders_guild_then_get_points_from_stages() {
		Card guildCard = new BuildersGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.withPlayerBuiltStages("test1", 1)
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}
	
	@Test
	public void when_choosing_strategists_guild_then_get_points_from_neighbor_defeats() {
		Card guildCard = new StrategistsGuild(3,3);
		Game testGame = 
				UnitTestCaseBuilder.create()
				.withPlayerDefeats("test2", 1)
				.withPlayerNextAction("test1", new ChooseGuild(Arrays.asList(guildCard)))
				.buildGame();
		
		ChooseGuildRequest r = getRequest(guildCard);
		testGame.getPlayer("test1").doAction(r, testGame);
		
		Assertions.assertEquals(1, testGame.getPlayer("test1").getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}
}
