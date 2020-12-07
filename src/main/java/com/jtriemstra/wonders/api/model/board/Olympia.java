package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsGuildCard;
import com.jtriemstra.wonders.api.model.action.provider.OlympiaOptionsProvider;
import com.jtriemstra.wonders.api.model.board.Alexandria.A1;
import com.jtriemstra.wonders.api.model.board.Alexandria.A2;
import com.jtriemstra.wonders.api.model.board.Alexandria.A3;
import com.jtriemstra.wonders.api.model.board.Alexandria.B1;
import com.jtriemstra.wonders.api.model.board.Alexandria.B2;
import com.jtriemstra.wonders.api.model.board.Alexandria.B3;
import com.jtriemstra.wonders.api.model.card.provider.CoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.NaturalTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Olympia extends Board {
	public Olympia(boolean sideA) {
		super(sideA);
		if (sideA) {
			stages = new WonderStages(new A1(), new A2(), new A3());
		}
		else {
			stages = new WonderStages(new B1(), new B2(), new B3());
		}
	}

	@Override
	public String getName() {
		return "Olympia";
	}
	
	@Override
	public ResourceSet getStartingResource() {
		return new ResourceSet(ResourceType.WOOD);
	}
	
	public class A1 extends WonderStage {
		@Override
		public void build(Player p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD};
		}
	}
	
	public class A2 extends WonderStage {
		@Override
		public void build(Player p, Game game) {
			p.setOptionsFactory(new OlympiaOptionsProvider());
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.STONE, ResourceType.STONE};
		}
	}
	
	public class A3 extends WonderStage {
		@Override
		public void build(Player p, Game game) {
			p.addVPProvider(new SimpleVPProvider(7, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.ORE, ResourceType.ORE};
		}
	}
	
	public class B1 extends WonderStage {
		
		@Override
		public void build(Player p, Game game) {
			p.addTradingProvider(new NaturalTradingProvider(CardDirection.BOTH));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD};
		}
	}
	
	public class B2 extends WonderStage {
		
		@Override
		public void build(Player p, Game game) {
			p.addVPProvider(new SimpleVPProvider(5, VictoryPointType.STAGES));			
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.STONE, ResourceType.STONE};
		}
	}
	
	public class B3 extends WonderStage {
		
		@Override
		public void build(Player p, Game game) {
			game.addPostGameAction(p, new GetOptionsGuildCard());
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.ORE, ResourceType.ORE, ResourceType.TEXTILE};
		}
	}
}
