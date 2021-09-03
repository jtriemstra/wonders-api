package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.leaders.LeaderCard;

public class GetOptionsRecruitLeader extends GetOptions {

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		
		if (player.getHandSize() == 0 || !(player.getHandCards()[0] instanceof LeaderCard)) {
			player.moveLeadersToHand();
		}
		
		return super.execute(request, player, game);
		
	}

	@Override
	protected BaseAction createDiscardAction(Player player, Game game) {
		return new DiscardLeader();
	}
}
