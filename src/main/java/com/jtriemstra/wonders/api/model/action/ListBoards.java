package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.ListBoardResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.ScienceType;

public class ListBoards implements BaseAction {

	@Override
	public String getName() {
		return "listBoards";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();

		player.addNextAction(new ListBoards(), new ChooseBoard());
		
		ListBoardResponse r = new ListBoardResponse();
		r.setBoards(game.getBoardsInUse());
		
		return r;
	}

}
