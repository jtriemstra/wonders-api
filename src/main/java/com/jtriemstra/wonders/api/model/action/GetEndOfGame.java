package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.GetEndOfAgeResponse;
import com.jtriemstra.wonders.api.dto.response.GetEndOfGameResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class GetEndOfGame implements BaseAction {

	@Override
	public String getName() {
		return "finishGame";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();
		player.addNextAction(new Quit());
		
		GetEndOfGameResponse r = new GetEndOfGameResponse();

		r.setDefeats(player.getNumberOfDefeats(game.getCurrentAge()));
		r.setVictories(player.getNumberOfVictories(game.getCurrentAge()));
		r.setAllVictoryPoints(player.getFinalVictoryPoints());
		return r;
	}

}
