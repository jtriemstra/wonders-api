package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseBoardRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.exceptions.BoardInUseException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ChooseBoard implements BaseAction {

	private BoardManager boardManager;
	
	@Override
	public String getName() {
		return "chooseBoard";
	}

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		ChooseBoardRequest chooseRequest = (ChooseBoardRequest) request;
				
		if (!chooseRequest.isSkip()) {
			String boardName = chooseRequest.getBoardName();
			try {
				Board b = boardSwap(player.getBoardName(), boardName, chooseRequest.getBoardSide().equals("A"));
				player.setBoard(b);	
			}
			catch (BoardInUseException e) {
				// TODO: (low) put messaging around this?
			}
		} else {
			player.popAction();
			Wait w = new WaitBoards();
			player.addNextAction(w);
		}
		
		return new ActionResponse();
		
	}
	
	private Board boardSwap(String oldName, String newName, boolean sideA) throws BoardInUseException {
		Board b = boardManager.swap(oldName, newName, sideA);
		return b;		
	}

}
