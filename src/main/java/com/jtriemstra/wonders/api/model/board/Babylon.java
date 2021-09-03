package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptions;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.action.PlayCardsAction;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.ResolveCommerceAction;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.phases.AgePhase;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Babylon extends Board {
	public Babylon(boolean sideA) {
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
		return "Babylon";
	}
	
	@Override
	public ResourceSet getStartingResource() {
		return new ResourceSet(ResourceType.BRICK);
	}

	@Override
	public void addStartingBenefit(Player player, Game game) {
		player.addResourceProvider(() -> new ResourceSet(ResourceType.BRICK), true);
	}
	
	public class A1 extends WonderStage {
		@Override
		public void build(Player p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK};
		}
	}
	
	public class A2 extends WonderStage {
		@Override
		public void build(Player p, Game game) {
			game.getFlow().addPostGameAction(p, new GetOptionsScience(), AgePhase.class);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD, ResourceType.WOOD};
		}
	}
	
	public class A3 extends WonderStage {
		@Override
		public void build(Player p, Game game) {
			p.addVPProvider(new SimpleVPProvider(7, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK, ResourceType.BRICK, ResourceType.BRICK};
		}
	}
	
	public class B1 extends WonderStage {
		
		@Override
		public void build(Player p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.TEXTILE};
		}
	}
	
	public class B2 extends WonderStage {
		
		@Override
		public void build(Player p, Game game) {
			//TODO: this needs to work in the ChooseLeaderPhase as well
			game.getFlow().addPostTurnAction(p, new GetOptionsBabylon(), (phase, flow) -> {return phase instanceof AgePhase;} );
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.WOOD, ResourceType.WOOD, ResourceType.GLASS};
		}
	}
	
	public class B3 extends WonderStage {
		@Override
		public void build(Player p, Game game) {
			game.getFlow().addPostGameAction(p, new GetOptionsScience(), AgePhase.class);
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.BRICK, ResourceType.BRICK, ResourceType.PAPER};
		}
	}
	
	public class GetOptionsBabylon extends GetOptions implements PostTurnAction {

		@Override
		public double getOrder() {
			return .5;
		}

		@Override
		public ActionResponse execute(BaseRequest request, Player player, Game game) {
			
			player.popAction();
			
			if (!game.getFlow().isAgeStarted() || !game.getFlow().isFinalTurn()) {
				return new WaitResponse();
			}

			game.getFlow().injectPostTurnAction(player, new PlayCardsAction(player, .5), 1, (phase, flow) -> {return phase instanceof AgePhase; });
			game.getFlow().injectPostTurnAction(player, new ResolveCommerceAction(player), 2, (phase, flow) -> {return phase instanceof AgePhase; });
			
			return buildResponse(player, game);
		}
		
	}
}
