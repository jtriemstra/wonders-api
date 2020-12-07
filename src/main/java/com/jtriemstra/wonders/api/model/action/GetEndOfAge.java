package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.GetEndOfAgeResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class GetEndOfAge implements BaseAction {

	@Override
	public String getName() {
		return "getEndOfAge";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();
		player.addNextAction(new StartAge());
		
		GetEndOfAgeResponse r = new GetEndOfAgeResponse();

		r.setDefeats(player.getNumberOfDefeats(game.getCurrentAge()));
		r.setVictories(player.getNumberOfVictories(game.getCurrentAge()));
		r.setAllDefeats(player.getNumberOfDefeats());
		r.setAllVictories(player.getVictories());
		r.setAge(game.getCurrentAge());
		return r;
	}

}
