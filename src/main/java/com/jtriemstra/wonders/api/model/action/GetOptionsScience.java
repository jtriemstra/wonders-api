package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsScienceResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.ScienceType;

public class GetOptionsScience implements BaseAction, PostTurnAction {

	@Override
	public String getName() {
		return "options";
	}

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		player.popAction();
		
		if (!game.getFlow().isFinalTurn() || !game.getFlow().isFinalAge()) {
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
