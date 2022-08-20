package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.action.GetOptionsGuildCard;
import com.jtriemstra.wonders.api.model.action.provider.OlympiaOptionsProvider;
import com.jtriemstra.wonders.api.model.card.provider.NaturalTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.phases.AgePhase;
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

	@Override
	public Board.StartingBenefit getStartingBenefit() {
		return (player, game) -> {
			player.addResourceProvider(() -> new ResourceSet(ResourceType.WOOD), true);
		};
	}
	
	@Override
	public String[] getHelpText() {
		if (sideA) {
			return new String[] {"If you build this stage, you get 3 points at the end of the game", "If you build this stage, once per age from now until the end of the game, you will have the opportunity to play a card regardless of whether or not you have the resources it requires.", "If you build this stage, you get 7 points at the end of the game"};
		}
		else {
			return new String[] {"If you build this stage, you are able to purchse brown card resources (wood, brick, ore, stone) from the neighbors to your left or right for 1 coin instead of 2.", "If you build this stage, you get 5 points at the end of the game", "If you build this stage, at the end of the game you will have the ability to choose one purple card that was played by your left or right neighbor, and you will receive points as though you had played it."};
		}
	}
	
	public class A1 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD};
		}
	}
	
	public class A2 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.setOptionsFactory(new OlympiaOptionsProvider());
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.STONE, ResourceType.STONE};
		}
	}
	
	public class A3 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(7, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.ORE, ResourceType.ORE};
		}
	}
	
	public class B1 extends WonderStage {
		
		@Override
		public void build(IPlayer p, Game game) {
			p.addTradingProvider(new NaturalTradingProvider(CardDirection.BOTH));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD};
		}
	}
	
	public class B2 extends WonderStage {
		
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(5, VictoryPointType.STAGES));			
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.STONE, ResourceType.STONE};
		}
	}
	
	public class B3 extends WonderStage {
		
		@Override
		public void build(IPlayer p, Game game) {
			game.getFlow().addPostGameAction(p, new GetOptionsGuildCard(), AgePhase.class);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.ORE, ResourceType.ORE, ResourceType.TEXTILE};
		}
	}
}
