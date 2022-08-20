package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.SimpleCoinProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Ephesus extends Board {
	public Ephesus(boolean sideA) {
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
		return "Ephesus";
	}
	
	@Override
	public ResourceSet getStartingResource() {
		return new ResourceSet(ResourceType.PAPER);
	}

	@Override
	public Board.StartingBenefit getStartingBenefit() {
		return (player, game) -> {
			player.addResourceProvider(() -> new ResourceSet(ResourceType.PAPER), true);
		};
	}
	
	@Override
	public String[] getHelpText() {
		if (sideA) {
			return new String[] {"If you build this stage, you get 3 points at the end of the game", "If you build this stage, you immediately receive 9 coins", "If you build this stage, you get 7 points at the end of the game"};
		}
		else {
			return new String[] {"If you build this stage, you get 2 points at the end of the game and immediately receive 4 coins", "If you build this stage, you get 3 points at the end of the game and immediately receive 4 coins", "If you build this stage, you get 5 points at the end of the game and immediately receive 4 coins"};
		}
	}
	
	public class A1 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.STONE, ResourceType.STONE};
		}
	}
	
	public class A2 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.gainCoins(9);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD};
		}
	}
	
	public class A3 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(7, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.PAPER, ResourceType.PAPER};
		}
	}
	
	public class B1 extends WonderStage {
		
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(2, VictoryPointType.STAGES));
			p.gainCoins(4);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.STONE, ResourceType.STONE};
		}
	}
	
	public class B2 extends WonderStage {
		
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
			p.gainCoins(4);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD};
		}
	}
	
	public class B3 extends WonderStage {
		
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(5, VictoryPointType.STAGES));
			p.gainCoins(4);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.PAPER, ResourceType.GLASS, ResourceType.TEXTILE};
		}
	}
}
