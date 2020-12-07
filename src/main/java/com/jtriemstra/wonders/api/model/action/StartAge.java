package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class StartAge implements BaseAction {

	@Override
	public String getName() {
		return "startAge";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();
		game.startAge();
		player.startTurn();
				
		return new WaitResponse();
	}

}
