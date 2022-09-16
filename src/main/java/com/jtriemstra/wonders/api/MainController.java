package com.jtriemstra.wonders.api;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.BuildRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseBoardRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseGuildRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseScienceRequest;
import com.jtriemstra.wonders.api.dto.request.CreateRequest;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.dto.request.GetEndOfAgeRequest;
import com.jtriemstra.wonders.api.dto.request.GetEndOfGameRequest;
import com.jtriemstra.wonders.api.dto.request.JoinRequest;
import com.jtriemstra.wonders.api.dto.request.KeepLeaderRequest;
import com.jtriemstra.wonders.api.dto.request.ListBoardsRequest;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.request.PlayFreeRequest;
import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.dto.request.RefreshRequest;
import com.jtriemstra.wonders.api.dto.request.ShowLeaderRequest;
import com.jtriemstra.wonders.api.dto.request.StartAgeRequest;
import com.jtriemstra.wonders.api.dto.request.StartRequest;
import com.jtriemstra.wonders.api.dto.request.UpdateGameRequest;
import com.jtriemstra.wonders.api.dto.request.WaitRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.dto.response.ChooseBoardResponse;
import com.jtriemstra.wonders.api.dto.response.CreateJoinResponse;
import com.jtriemstra.wonders.api.dto.response.ListGameResponse;
import com.jtriemstra.wonders.api.dto.response.NeighborInfo;
import com.jtriemstra.wonders.api.dto.response.RefreshResponse;
import com.jtriemstra.wonders.api.dto.response.WaitResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.GameList;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.Build;
import com.jtriemstra.wonders.api.model.action.PossibleActions;
import com.jtriemstra.wonders.api.model.action.UpdateGame;
import com.jtriemstra.wonders.api.model.action.WaitPlayers;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayableComparator;

@RestController
@CrossOrigin(origins = {"http://localhost:8001", "https://master.d1rb5aud676z7x.amplifyapp.com"})
public class MainController {
	@Autowired
	private GameList games;
	
	@Autowired
	private GameFactory gameFactory;
	
	@Autowired
	@Qualifier("createPlayerFactory")
	private PlayerFactory playerFactory;

	@WondersLogger
	@RequestMapping("/create")
	public CreateJoinResponse createGame(CreateRequest request, HttpServletRequest servletRequest) {
				
		CreateJoinResponse r = new CreateJoinResponse();
		r.setNextActions(new PossibleActions(new UpdateGame()));
		return r;
	}

	@WondersLogger
	@RequestMapping("/updateGame")
	public ActionResponse updateGame(UpdateGameRequest request, HttpServletRequest servletRequest) {
		
		Game g = gameFactory.createGame(request.getPlayerId(), request.getNumberOfPlayers(), request.isLeaders(), request.getSideOptions() == null ? BoardSide.A_OR_B : request.getSideOptions(), request.isChooseBoard());
				
		IPlayer p = playerFactory.createPlayer(request.getPlayerId()); 
		
		g.addPlayer(p);
		
		games.add(request.getPlayerId(), g);
		
		p.addNextAction(new WaitPlayers());
		
		CreateJoinResponse r = new CreateJoinResponse();
		r.setNextActions(p.getNextAction());
		r.setBoardName(p.getBoardName());
		r.setBoardSide(p.getBoardSide());
		r.setBoardHelp(p.getBoardHelp());
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/join")
	public CreateJoinResponse joinGame(JoinRequest request, HttpServletRequest servletRequest) {
		
		IPlayer p = playerFactory.createPlayer(request.getPlayerName());
		games.get(request.getGameName()).addPlayer(p);
		p.addNextAction(new WaitPlayers());
		
		CreateJoinResponse r = new CreateJoinResponse();
		r.setNextActions(p.getNextAction());
		r.setBoardName(p.getBoardName());
		r.setBoardSide(p.getBoardSide());
		r.setAge(1);
		r.setBoardHelp(p.getBoardHelp());
		return r;
	}
		
	@WondersLogger
	@RequestMapping("/listGames")
	public ListGameResponse listGames() {
		ListGameResponse r = new ListGameResponse();
		r.setGames(games);
		
		return r;
	}
	
	//TODO: maybe could make this another options call?
	@WondersLogger
	@RequestMapping("/listBoards")
	public ActionResponse listBoards(ListBoardsRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/chooseBoard")
	public ChooseBoardResponse chooseBoard(ChooseBoardRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		
		ActionResponse r = p.doAction(request,g);
		ChooseBoardResponse r1 = new ChooseBoardResponse(r);
		r1.setBoardName(p.getBoardName());
		r1.setBoardSide(p.getBoardSide());
		
		return r1;
	}
	
	@WondersLogger
	@RequestMapping("/start")
	public ActionResponse startGame(StartRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/options")
	public ActionResponse options(OptionsRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
				
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/play")
	public ActionResponse play(PlayRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/playFree")
	public ActionResponse playFree(PlayFreeRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/build")
	public ActionResponse build(BuildRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/chooseScience")
	public ActionResponse chooseScience(ChooseScienceRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/chooseGuild")
	public ActionResponse chooseGuild(ChooseGuildRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		request.setOptionName(URLDecoder.decode(URLDecoder.decode(request.getOptionName())));
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/discard")
	public ActionResponse discard(DiscardRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/wait")
	public BaseResponse wait(WaitRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
		
	}

	@WondersLogger
	@RequestMapping("/refresh")
	public BaseResponse refresh(RefreshRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = null;
		
		if (g != null) {
			p = g.getPlayer(request.getPlayerId());
		}
		
		// TODO (low): save the player state as it changes, 
		RefreshResponse r = new RefreshResponse();
		if (p != null) {
			r.setBoardName(p.getBoardName());
			r.setBoardSide(p.getBoardSide());
			r.setBoardHelp(p.getBoardHelp());
			r.setCardsOnBoard(p.getPlayedCards());
			r.setCoins(p.getCoins());
			r.setNextActions(p.getNextAction());
			List<CardPlayable> playableCards = p.getPlayableCards(g.getLeftOf(p), g.getRightOf(p), p.getHandCards());
			playableCards.sort(new CardPlayableComparator());
			r.setCards(playableCards);
			r.setBuildState(p.getBuildState());	
			r.setPlayerFound(true);
			if (p.getOptions() != null) {
				r.setOptions(p.getOptions());
			}
			r.setLeftNeighbor(new NeighborInfo(g.getLeftOf(p)));
			r.setRightNeighbor(new NeighborInfo(g.getRightOf(p)));
			r.setAllDefeats(p.getArmyFacade().getNumberOfDefeats());
			r.setAllVictories(p.getArmyFacade().getVictories());
			if (p.getNextAction().toString().contains("build")) {
				Build b = (Build) p.getNextAction().getByName("build");
				r.setBuildCost(b.getBuildable());
			}
		}
		else {
			r.setPlayerFound(false);
			
			return r;
		}
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/getEndOfAge")
	public BaseResponse getEndOfAge(GetEndOfAgeRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/finishGame")
	public BaseResponse finishGame(GetEndOfGameRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/startAge")
	public BaseResponse startAge(StartAgeRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/endGame")
	public BaseResponse endGame(BaseRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		games.remove(request.getGameName());
		
		g = null;
		
		return new BaseResponse();
	}
	
	@WondersLogger
	@RequestMapping("/keepLeader")
	public ActionResponse keepLeader(KeepLeaderRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/showLeaders")
	public ActionResponse showLeaders(ShowLeaderRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request,g);
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/finishShowLeaders")
	public ActionResponse finishShowLeaders(BaseRequest request, HttpServletRequest servletRequest) {
		Game g = games.get(request.getGameName());
		IPlayer p = g.getPlayer(request.getPlayerId());
		
		p.popAction();
		ActionResponse r = new WaitResponse();
		r.setNextActions(p.getNextAction());
		
		return r;
	}
}
