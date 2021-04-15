package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.UpdateGameRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.ChooseBoardFactory;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.deck.leaders.GuildCardFactoryLeaders;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBoard;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryLeader;
import com.jtriemstra.wonders.api.model.phases.Phases;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacadeLeaders;

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
				
		CardFactory guildFactory = new GuildCardFactoryBasic();		
		GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic();
		
		if (updateRequest.isChooseBoard()) {
			BoardFactory bf = new ChooseBoardFactory();
			game.setBoardFactory(bf);	
			game.setDefaultPlayerReady(false);
			phaseFactory = new GamePhaseFactoryBoard(phaseFactory);
		}
		
		if (updateRequest.getSideOptions() != BoardSide.A_OR_B && updateRequest.getSideOptions() != null) {
			game.setBoardSideOptions(updateRequest.getSideOptions());
		}
		
		if (updateRequest.isLeaders()) {
			//TODO: add leader board
			guildFactory = new GuildCardFactoryLeaders(guildFactory);
			phaseFactory = new GamePhaseFactoryLeader(phaseFactory);
			game.setInitialCoins(() -> 6);
			game.setDefaultCalculation(() -> new VictoryPointFacadeLeaders());
		}
		
		game.setDeckFactory(new DefaultDeckFactory(new AgeCardFactory(), guildFactory));
		game.setPhases(new Phases(phaseFactory));
		
		game.addPlayer(player);
		
		game.setReady(true);
		
		player.addNextAction(new WaitPlayers());

		return new WaitResponse();
	}

}
