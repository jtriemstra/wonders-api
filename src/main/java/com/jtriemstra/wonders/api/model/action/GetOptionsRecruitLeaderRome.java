package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.leaders.PlayerLeaders;

public class GetOptionsRecruitLeaderRome extends GetOptions implements PostTurnAction {

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		
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
	protected CardRemoveStrategy getRemoval(IPlayer player) {
		return cardName -> ((PlayerLeaders)player).removeCardFromLeaders(cardName);
	}
	
	@Override
	protected Card[] getCardsToEvaluate(IPlayer p) {
		return ((PlayerLeaders)p).getLeaderCards();
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
