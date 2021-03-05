package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class StartGame implements BaseAction {

	@Override
	public String getName() {
		return "start";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();
		//TODO: allow a variation for picking boards/leaders before starting the game, maybe in the Game class
		game.startGame();
		
		return new ActionResponse();
	}

}
