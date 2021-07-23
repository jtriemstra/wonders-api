package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class UpdateGame implements BaseAction {

	//TODO (low): remove this class, it's just a flag for the front end right now
	
	@Override
	public String getName() {
		return "updateGame";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		return null;
	}

}
