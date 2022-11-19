package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.GetEndOfAgeResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;

public class GetEndOfAge implements BaseAction {

	@Override
	public String getName() {
		return "getEndOfAge";
	}

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		player.popAction();
		
		GetEndOfAgeResponse r = new GetEndOfAgeResponse();

		r.setDefeats(player.getArmyFacade().getDefeats(game.getFlow().getCurrentAge()));
		r.setVictories(player.getArmyFacade().getVictories(game.getFlow().getCurrentAge()));
		r.setAllDefeats(player.getArmyFacade().getNumberOfDefeats());
		r.setAllVictories(player.getArmyFacade().getVictories());
		r.setAge(game.getFlow().getCurrentAge());
		return r;
	}

}
