package com.jtriemstra.wonders.api.model.board;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.action.GetOptionsRecruitLeaderRome;
import com.jtriemstra.wonders.api.model.action.ShowAdditionalLeaders;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.LeaderCard;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.deck.Deck;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;
import com.jtriemstra.wonders.api.model.leaders.PlayerLeaders;
import com.jtriemstra.wonders.api.model.playbuildrules.leaders.CoinDiscountByType;
import com.jtriemstra.wonders.api.model.playbuildrules.leaders.FreeByType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Rome extends Board {
	private LeaderDeck leaderDeck;
	
	public Rome(boolean sideA, LeaderDeck leaderDeck) {
		super(sideA);
		this.leaderDeck = leaderDeck;
		if (sideA) {
			stages = new WonderStages(new A1(), new A2());
		}
		else {
			stages = new WonderStages(new B1(), new B2(), new B3());
		}
	}

	@Override
	public String getName() {
		return "Rome";
	}
	
	@Override
	public ResourceSet getStartingResource() {
		return null;
	}

	@Override
	public void addStartingBenefit(IPlayer player, Game game) {
		if (sideA) {
			player.addPlayRule(new FreeByType(LeaderCard.class));
		}
		else {
			player.addPlayRule(new CoinDiscountByType(LeaderCard.class, 2));
			game.getLeftOf(player).addPlayRule(new CoinDiscountByType(LeaderCard.class, 1));
			game.getRightOf(player).addPlayRule(new CoinDiscountByType(LeaderCard.class, 1));
		}
	}

	public class A1 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(4, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.WOOD, ResourceType.ORE};
		}
	}

	public class A2 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(6, VictoryPointType.STAGES));
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.STONE, ResourceType.STONE, ResourceType.TEXTILE};
		}
	}
	
	public class B1 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.gainCoins(5);
			Deck unusedLeaders = leaderDeck;
			List<Card> newLeaders = new ArrayList<>();
			for (int i=0; i<4; i++) {
				Card c = unusedLeaders.draw();
				newLeaders.add(c);
				((PlayerLeaders) p).keepLeader(c);
			}
			
			game.getFlow().addPostTurnAction(p, new ShowAdditionalLeaders(newLeaders), (phase, flow) -> {return (phase == flow.getCurrentPhase()); });
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.WOOD};
		}
	}

	public class B2 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
			game.getFlow().addPostTurnAction(p, new GetOptionsRecruitLeaderRome(), (phase, flow) -> {return (phase == flow.getCurrentPhase()); });
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.BRICK, ResourceType.STONE, ResourceType.TEXTILE};
		}
	}

	public class B3 extends WonderStage {
		@Override
		public void build(IPlayer p, Game game) {
			p.addVPProvider(new SimpleVPProvider(3, VictoryPointType.STAGES));
			game.getFlow().addPostTurnAction(p, new GetOptionsRecruitLeaderRome(), (phase, flow) -> {return (phase == flow.getCurrentPhase()); });
		}
		
		@Override
		public ResourceType[] getResourceCost() {
			return new ResourceType[] {ResourceType.STONE, ResourceType.STONE, ResourceType.PAPER};
		}
	}
}
