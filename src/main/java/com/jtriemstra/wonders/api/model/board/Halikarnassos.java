package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.action.GetOptionsFromDiscard;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.phases.AgePhase;
import com.jtriemstra.wonders.api.model.phases.ChooseLeaderPhase;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Halikarnassos extends Board {
	
	public Halikarnassos(boolean sideA) {
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
		return "Halikarnassos";
	}
	
	@Override
	public ResourceSet getStartingResource() {
		return new ResourceSet(ResourceType.TEXTILE);
	}

	@Override
	public Board.StartingBenefit getStartingBenefit() {
		return (player, game) -> {
			player.addResourceProvider(() -> new ResourceSet(ResourceType.TEXTILE), true);
		};
	}
	
	@Override
	public String[] getHelpText() {
		if (sideA) {
			return new String[] {"If you build this stage, you get 3 points at the end of the game", "If you build this stage, you will immediately look at the discard pile, and have the opportunity to play one card from it, regardless of whether you have enough resources", "If you build this stage, you get 7 points at the end of the game"};
		}
		else {
			return new String[] {"If you build this stage, you will immediately look at the discard pile, and have the opportunity to play one card from it, regardless of whether you have enough resources. You also receive 2 points at the end of the game.", "If you build this stage, you will immediately look at the discard pile, and have the opportunity to play one card from it, regardless of whether you have enough resources. You also receive 1 point at the end of the game", "If you build this stage, you will immediately look at the discard pile, and have the opportunity to play one card from it, regardless of whether you have enough resources"};
		}
	}
	
	public class A1 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK};
		}
	}
	
	public class A2 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			game.getFlow().addPostTurnAction(p, new GetOptionsFromDiscard(), 
					(phase, flow) -> {return phase == flow.getCurrentPhase(); }
			);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.ORE, ResourceType.ORE, ResourceType.ORE};
		}
	}
	
	public class A3 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(7, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.TEXTILE, ResourceType.TEXTILE};
		}
	}
	
	public class B1 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			game.getFlow().addPostTurnAction(p, new GetOptionsFromDiscard(), 
					(phase, flow) -> {return phase == flow.getCurrentPhase(); }
			);
			p.addVPProvider(new SimpleVPProvider(2, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.ORE, ResourceType.ORE};
		}
	}
	
	public class B2 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			game.getFlow().addPostTurnAction(p, new GetOptionsFromDiscard(), 
					(phase, flow) -> {return phase == flow.getCurrentPhase(); }
			);
			p.addVPProvider(new SimpleVPProvider(1, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK, ResourceType.BRICK};
		}
	}

	public class B3 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			game.getFlow().addPostTurnAction(p, new GetOptionsFromDiscard(), 
					(phase, flow) -> {return phase == flow.getCurrentPhase(); } 
			);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.GLASS, ResourceType.PAPER, ResourceType.TEXTILE};
		}
	}
}
