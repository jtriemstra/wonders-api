package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsScienceResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.ScienceType;

public class GetOptionsScience implements BaseAction, PostTurnAction {

	@Override
	public String getName() {
		return "options";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();
		
		if (!game.isFinalTurn() || !game.isFinalAge()) {
			return new WaitResponse();
		}
		
		player.addNextAction(new ChooseScience());
		
		OptionsScienceResponse r = new OptionsScienceResponse();
		r.setOptions(new ScienceType[] {ScienceType.TABLET, ScienceType.GEAR, ScienceType.COMPASS});
		
		return r;
	}

	@Override
	public double getOrder() {
		return 5;
	}

}
