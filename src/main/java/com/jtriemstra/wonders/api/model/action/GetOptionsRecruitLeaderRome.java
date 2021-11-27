package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.leaders.LeaderCard;
import com.jtriemstra.wonders.api.model.phases.AgePhase;
import com.jtriemstra.wonders.api.model.phases.ChooseLeaderPhase;

import lombok.AllArgsConstructor;

public class GetOptionsRecruitLeaderRome extends GetOptions implements PostTurnAction {

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		
		if (player.getHandSize() == 0 || !(player.getHandCards()[0] instanceof LeaderCard)) {
			player.moveLeadersToHand();
		}
		game.getFlow().injectPostTurnAction(player, new PlayCardsAction(player, .05), 1, (phase, flow) -> {return phase == flow.getCurrentPhase(); });
		game.getFlow().injectPostTurnAction(player, new Reset(player), 3, (phase, flow) -> {return phase == flow.getCurrentPhase(); });

				
		return super.execute(request, player, game);
		
	}

	@Override
	protected ActionFactory[] getValidActionFactories() {
		return new ActionFactory[] {
				(p, g) -> createPlayAction(p, g)
		};
	}
	
	@Override
	public double getOrder() {
		return 1.1;
	}
	
	@Override
	public boolean isSingleUse() {
		return true;
	}

	@AllArgsConstructor
	public class Reset implements NonPlayerAction, PostTurnAction {
		
		private Player player;
		
		@Override
		public ActionResponse execute(Game game) {
			player.clearHand();
			player.restoreAgeCards();			
			return null;
		}
		
		@Override
		public double getOrder() {
			return 1.2;
		}

		@Override
		public String getName() {
			return "reset";
		}
		
		@Override
		public boolean isSingleUse() {
			return true;
		}
		
	}
}
