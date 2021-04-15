package com.jtriemstra.wonders.api.model.resource;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.Game;
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

public class ItemCostUnitTests {
	@Test
	public void when_have_resources_scientists_guild_costs_zero() { 
		CardList cl = new CardList();
		
		Player p = new Player("test", new ActionList(), new ArrayList<>(), new ArrayList<>(), cl);
		p.addResourceProvider(() -> new ResourceSet(ResourceType.STONE), true);
		p.addResourceProvider(() -> new ResourceSet(ResourceType.STONE), true);
		p.addResourceProvider(() -> new ResourceSet(ResourceType.PAPER), true);
		p.addResourceProvider(() -> new ResourceSet(ResourceType.WOOD), true);
		p.addResourceProvider(() -> new ResourceSet(ResourceType.BRICK, ResourceType.ORE), true);
		p.addResourceProvider(() -> new ResourceSet(ResourceType.GLASS), true);
		p.addResourceProvider(() -> new ResourceSet(ResourceType.WOOD, ResourceType.ORE, ResourceType.BRICK, ResourceType.STONE), false);
		p.addTradingProvider(new NaturalTradingProvider(CardDirection.BOTH));
		Board b = new Olympia(false);
		p.setBoard(b);
		b.addStartingBenefit(p, Mockito.mock(Game.class));
		
		CardList cl2 = new CardList();
		cl2.add(new Foundry(2,3));
		cl2.add(new Glassworks(2,3));
		cl2.add(new ClayPool(1,3));
		cl2.add(new Loom(1,3));
		cl2.add(new StonePit(1,3));
		Player p2 = new Player("test2", new ActionList(), new ArrayList<>(), new ArrayList<>(), cl2);
		p2.setBoard(new Olympia(false));
		
		CardList cl3 = new CardList();
		cl3.add(new Loom(2,3));
		cl3.add(new Sawmill(2,3));
		cl3.add(new Press(2,3));
		cl3.add(new Brickyard(2,3));
		cl3.add(new OreVein(1,3));
		cl3.add(new TimberYard(1,3));
		Player p3 = new Player("test3", new ActionList(), new ArrayList<>(), new ArrayList<>(), cl3);
		p3.setBoard(new Olympia(false));
		
		CardPlayable cp = p.canPlay(new ScientistsGuild(3,3), p2, p3);
		Assertions.assertEquals(0, cp.getCost());
		Assertions.assertEquals(Status.OK, cp.getStatus());
		
	}
}
