package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;

public class Quit implements BaseAction {

	@Override
	public String getName() {
		return "quit";
	}

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		return new ActionResponse();
	}

}
