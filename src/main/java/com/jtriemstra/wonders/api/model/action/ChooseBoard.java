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
			int boardId = Board.getId(chooseRequest.getBoardName());
			Board b = game.boardSwap(player.getBoardId(), boardId, chooseRequest.getBoardSide().equals("A"));
			player.setBoard(b);	
		}
		
		Wait w = new WaitBoards();
		player.addNextAction(w);
		if (game.notifyWaiting(Wait.For.BOARDS, w)) {
			//TODO: this is a mess, and won't scale to expansions
			Game.StartStrategy realStart = game.new StartStrategyDefault();
			realStart.execute();
		}
		else {
			player.addNextAction(new WaitBoards());
		}
		
		return new ActionResponse();
		
	}

}
