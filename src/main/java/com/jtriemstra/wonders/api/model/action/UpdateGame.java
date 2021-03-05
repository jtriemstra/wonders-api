package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.UpdateGameRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Game.BoardSide;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.board.ChooseBoardFactory;

public class UpdateGame implements BaseAction {

	@Override
	public String getName() {
		return "updateGame";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();
		
		UpdateGameRequest updateRequest = (UpdateGameRequest) request;
		
		game.setNumberOfPlayersExpected(updateRequest.getNumberOfPlayers());
		if (updateRequest.isChooseBoard()) {
			BoardFactory bf = new ChooseBoardFactory();
			game.setBoardFactory(bf);
			game.setStartStrategy(game.new StartStrategyChooseBoard());			
		}
		if (updateRequest.getSideOptions() != BoardSide.A_OR_B && updateRequest.getSideOptions() != null) {
			game.setBoardSideOptions(updateRequest.getSideOptions());
		}
		
		player.setBoard(game.getNextBoard());
		
		game.isReady(true);
		
		player.addNextAction(new WaitPlayers(player));

		return new WaitResponse();
	}

}
