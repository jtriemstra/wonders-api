package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.GetEndOfAgeResponse;
import com.jtriemstra.wonders.api.dto.response.GetEndOfGameResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;

public class GetEndOfGame implements BaseAction {

	@Override
	public String getName() {
		return "finishGame";
	}

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		player.popAction();
		player.addNextAction(new Quit());
		
		GetEndOfGameResponse r = new GetEndOfGameResponse();

		r.setDefeats(player.getArmyFacade().getNumberOfDefeats(game.getFlow().getCurrentAge()));
		r.setVictories(player.getArmyFacade().getNumberOfVictories(game.getFlow().getCurrentAge()));
		r.setAllVictoryPoints(player.getFinalVictoryPoints());
		return r;
	}

}
