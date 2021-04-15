package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;

public class GetOptionsRecruitLeaderRome extends GetOptions implements PostTurnAction {

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		
		player.moveLeadersToHand();
		
		return super.execute(request, player, game);
		
	}

	@Override
	public double getOrder() {
		return 1.1;
	}

}
