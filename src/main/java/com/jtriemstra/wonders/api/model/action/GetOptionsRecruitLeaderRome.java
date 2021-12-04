package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;

public class GetOptionsRecruitLeaderRome extends GetOptions implements PostTurnAction {

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		
		game.getFlow().injectPostTurnAction(player, new PlayCardsAction(player, .05), 1, (phase, flow) -> {return phase == flow.getCurrentPhase(); });
				
		return super.execute(request, player, game);
		
	}

	@Override
	protected ActionFactory[] getValidActionFactories() {
		return new ActionFactory[] {
				(p, g) -> createPlayAction(p, g)
		};
	}
	
	@Override
	protected Card[] getCardsToEvaluate(Player p) {
		return p.getLeaderCards();
	}
	
	@Override
	public double getOrder() {
		return 1.1;
	}
	
	@Override
	public boolean isSingleUse() {
		return true;
	}
}
