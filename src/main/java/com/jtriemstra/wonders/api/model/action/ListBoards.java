package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.ListBoardResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.board.BoardManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListBoards implements BaseAction {
	
	private BoardManager boardManager;

	@Override
	public String getName() {
		return "listBoards";
	}

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		player.popAction();

		player.addNextAction(new ListBoards(boardManager), new ChooseBoard(boardManager));
		
		ListBoardResponse r = new ListBoardResponse();
		r.setBoards(boardManager.listBoardsInUse());
		r.setSideAllowed(boardManager.getSides());
		
		return r;
	}

}
