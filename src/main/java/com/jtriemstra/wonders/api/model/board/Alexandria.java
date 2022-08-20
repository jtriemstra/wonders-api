package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Alexandria extends Board {
	public Alexandria(boolean sideA) {
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
		return "Alexandria";
	}
	
	@Override
	public ResourceSet getStartingResource() {
		return new ResourceSet(ResourceType.GLASS);
	}

	@Override
	public Board.StartingBenefit getStartingBenefit() {
		return (player, game) -> {
			player.addResourceProvider(() -> new ResourceSet(ResourceType.GLASS), true);
		};
	}
	
	@Override
	public String[] getHelpText() {
		if (sideA) {
			return new String[] {"If you build this stage, you get 3 points at the end of the game", "If you build this stage, it serves as a 'wild card' for brick, ore, wood, and stone. Each turn you can use it as one of these resources added to your brown cards.", "If you build this stage, you get 7 points at the end of the game"};
		}
		else {
			return new String[] {"If you build this stage, it serves as a 'wild card' for brick, ore, wood, and stone. Each turn you can use it as one of these resources added to your brown cards.","If you build this stage, it serves as a 'wild card' for glass, textile, and paper. Each turn you can use it as one of these resources added to your gray cards.","If you build this stage, you get 7 points at the end of the game"};
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
			p.addResourceProvider(() -> {return new ResourceSet(ResourceType.BRICK, ResourceType.ORE, ResourceType.WOOD, ResourceType.STONE);}, false);
		}
				
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.ORE, ResourceType.ORE};
		}
	}
	
	public class A3 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(7, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.GLASS, ResourceType.GLASS};
		}
	}
	
	public class B1 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addResourceProvider(() -> {return new ResourceSet(ResourceType.BRICK, ResourceType.ORE, ResourceType.WOOD, ResourceType.STONE);}, false);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK};
		}
	}
	
	public class B2 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addResourceProvider(() -> {return new ResourceSet(ResourceType.GLASS, ResourceType.PAPER, ResourceType.TEXTILE);}, false);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD};
		}
	}
	
	public class B3 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(7, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.STONE, ResourceType.STONE, ResourceType.STONE};
		}
	}
}
