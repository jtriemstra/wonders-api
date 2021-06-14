package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.UpdateGameRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.board.BoardSourceLeadersDecorator;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.deck.leaders.GuildCardFactoryLeaders;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderCardFactory;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;
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
		BoardSource boardSource = new BoardSourceBasic();
		
		//TODO: can I untangle this leader behavior better?
		LeaderDeck leaderDeck = null;
		if (updateRequest.isLeaders()) {
			leaderDeck = new LeaderDeck(new LeaderCardFactory());
			boardSource = new BoardSourceLeadersDecorator(boardSource, leaderDeck);
			guildFactory = new GuildCardFactoryLeaders(guildFactory);
			game.setInitialCoins(6);
			game.setDefaultCalculation(() -> new VictoryPointFacadeLeaders());
		}

		BoardManager newBoardManager = new BoardManager(boardSource, game.getBoardStrategy(), updateRequest.getSideOptions() == null ? BoardSide.A_OR_B : updateRequest.getSideOptions()); 
		
		DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory); 
		
		GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, updateRequest.getNumberOfPlayers());
		if (updateRequest.isLeaders()) {
			phaseFactory = new GamePhaseFactoryLeader(phaseFactory, leaderDeck);
		}
		if (updateRequest.isChooseBoard()) {
			phaseFactory = new GamePhaseFactoryBoard(phaseFactory, newBoardManager);
		}
		
		game.setBoardManager(newBoardManager);
		game.setDeckFactory(deckFactory);
		game.setPhases(new Phases(phaseFactory));
		
		game.addPlayer(player);
		
		game.setReady(true);
		
		player.addNextAction(new WaitPlayers());

		return new WaitResponse();
	}

}
