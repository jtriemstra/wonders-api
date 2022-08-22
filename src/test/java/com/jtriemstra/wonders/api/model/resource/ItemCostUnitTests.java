package com.jtriemstra.wonders.api.model.resource;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.Olympia;
import com.jtriemstra.wonders.api.model.card.Brickyard;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.ClayPool;
import com.jtriemstra.wonders.api.model.card.Foundry;
import com.jtriemstra.wonders.api.model.card.Glassworks;
import com.jtriemstra.wonders.api.model.card.Loom;
import com.jtriemstra.wonders.api.model.card.OreVein;
import com.jtriemstra.wonders.api.model.card.Press;
import com.jtriemstra.wonders.api.model.card.Sawmill;
import com.jtriemstra.wonders.api.model.card.ScientistsGuild;
import com.jtriemstra.wonders.api.model.card.StonePit;
import com.jtriemstra.wonders.api.model.card.TimberYard;
import com.jtriemstra.wonders.api.model.card.provider.NaturalTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.notifications.NotificationService;
import com.jtriemstra.wonders.api.state.MemoryStateService;

public class ItemCostUnitTests {
	@Test
	public void when_have_resources_scientists_guild_costs_zero() { 
		UnitTestCaseBuilder testBuilder = UnitTestCaseBuilder
				.create()
				.withPlayerNames("test1","test2","test3")
				.withInitialBoards("Olympia-B;Olympia-B;Olympia-B")
				.withPlayerCardsOnBoard("test2", new Foundry(2,3), new Glassworks(2,3), new ClayPool(1,3), new Loom(1,3), new StonePit(1,3))
				.withPlayerCardsOnBoard("test3", new Loom(2,3), new Sawmill(2,3), new Press(2,3), new Brickyard(2,3), new OreVein(1,3), new TimberYard(1,3))
				.withPlayerResourceProviders("test1", 
						() -> new ResourceSet(ResourceType.STONE),
						() -> new ResourceSet(ResourceType.STONE),
						() -> new ResourceSet(ResourceType.PAPER),
						() -> new ResourceSet(ResourceType.WOOD),
						() -> new ResourceSet(ResourceType.BRICK, ResourceType.ORE), 
						() -> new ResourceSet(ResourceType.GLASS),
						() -> new ResourceSet(ResourceType.WOOD, ResourceType.ORE, ResourceType.BRICK, ResourceType.STONE)
				)
				.withPlayerTradingProviders("test1", new NaturalTradingProvider(CardDirection.BOTH));
		
		Game g = testBuilder.buildGame();		
		
		IPlayer p = testBuilder.getPlayer("test1");
		p.getBoard().getStartingBenefit().claim(p, g);
		
		IPlayer p2 = testBuilder.getPlayer("test2");
		IPlayer p3 = testBuilder.getPlayer("test3");
		
		
		PlayableBuildableResult result = p.canPlay(new ScientistsGuild(3,3), p2, p3);
		CardPlayable cp = new CardPlayable(result.getCard(), result.getStatus(), result.getCostOptions(), result.getCost());
		
		Assertions.assertEquals(0, cp.getCostOptions().size());
		Assertions.assertEquals(Status.OK, cp.getStatus());
		
	}
}
