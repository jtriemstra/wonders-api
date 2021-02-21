package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseBoardRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.Board;

public class ChooseBoard implements BaseAction {

	@Override
	public String getName() {
		return "chooseBoard";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		ChooseBoardRequest chooseRequest = (ChooseBoardRequest) request;
		player.popAction();
		
		if (!chooseRequest.isSkip()) {
			Board b = game.boardSwap(player.getBoardId(), chooseRequest.getNewBoardId(), player.getBoardSide().equals("A"));
			player.setBoard(b);	
		}
		
		return new ActionResponse();
		
	}

}
