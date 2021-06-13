package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseBoardRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			String boardName = chooseRequest.getBoardName();
			Board b = game.boardSwap(player.getBoardName(), boardName, chooseRequest.getBoardSide().equals("A"));
			player.setBoard(b);	
		}
				
		Wait w = new WaitBoards();
		player.addNextAction(w);
		
		return new ActionResponse();
		
	}

}
