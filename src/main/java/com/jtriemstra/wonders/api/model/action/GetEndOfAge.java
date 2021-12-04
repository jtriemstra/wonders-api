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
		
		GetEndOfAgeResponse r = new GetEndOfAgeResponse();

		r.setDefeats(player.getArmyFacade().getNumberOfDefeats(game.getFlow().getCurrentAge()));
		r.setVictories(player.getArmyFacade().getNumberOfVictories(game.getFlow().getCurrentAge()));
		r.setAllDefeats(player.getArmyFacade().getNumberOfDefeats());
		r.setAllVictories(player.getArmyFacade().getVictories());
		r.setAge(game.getFlow().getCurrentAge());
		return r;
	}

}
