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
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBoard;
import com.jtriemstra.wonders.api.model.phases.Phases;

public class UpdateGame implements BaseAction {

	@Override
	public String getName() {
		return "updateGame";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		player.popAction();
		
		UpdateGameRequest updateRequest = (UpdateGameRequest) request;
		
		//TODO: should i not create the Game object until i have this data? then inject more dependencies in constructor instead of setters?
		game.setNumberOfPlayersExpected(updateRequest.getNumberOfPlayers());
		if (updateRequest.isChooseBoard()) {
			BoardFactory bf = new ChooseBoardFactory();
			game.setBoardFactory(bf);	
			game.setDefaultPlayerReady(false);
			game.setPhases(new Phases(new GamePhaseFactoryBoard(new GamePhaseFactoryBasic())));
			//TODO: don't like treating the creator different than other players here
			player.isReady(false);
		}
		else {
			game.setPhases(new Phases(new GamePhaseFactoryBasic()));
		}
		if (updateRequest.getSideOptions() != BoardSide.A_OR_B && updateRequest.getSideOptions() != null) {
			game.setBoardSideOptions(updateRequest.getSideOptions());
		}
		
		player.setBoard(game.getNextBoard());
		
		game.setReady(true);
		
		player.addNextAction(new WaitPlayers());

		return new WaitResponse();
	}

}
