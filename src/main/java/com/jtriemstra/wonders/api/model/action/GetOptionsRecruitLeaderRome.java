package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptions.ActionFactory;

import lombok.AllArgsConstructor;

public class GetOptionsRecruitLeaderRome extends GetOptions implements PostTurnAction {

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		
		player.moveLeadersToHand();
		game.removePostTurnAction(player, getClass());
		game.injectPostTurnAction(player, new PlayCardsAction(player, .05), 1);
		game.injectPostTurnAction(player, new ResolveCommerceAction(player), 2);
		game.injectPostTurnAction(player, new Reset(player), 3);

				
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

	@AllArgsConstructor
	public class Reset implements NonPlayerAction, PostTurnAction {
		
		private Player player;
		
		@Override
		public ActionResponse execute(Game game) {
			player.clearHand();
			player.restoreAgeCards();
			game.removePostTurnAction(player, getClass());
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
		
	}
}
