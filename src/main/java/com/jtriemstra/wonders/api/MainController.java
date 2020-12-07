package com.jtriemstra.wonders.api;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.BuildRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseGuildRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseScienceRequest;
import com.jtriemstra.wonders.api.dto.request.CreateRequest;
import com.jtriemstra.wonders.api.dto.request.DiscardRequest;
import com.jtriemstra.wonders.api.dto.request.GetEndOfAgeRequest;
import com.jtriemstra.wonders.api.dto.request.GetEndOfGameRequest;
import com.jtriemstra.wonders.api.dto.request.JoinRequest;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.request.PlayFreeRequest;
import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.dto.request.RefreshRequest;
import com.jtriemstra.wonders.api.dto.request.StartAgeRequest;
import com.jtriemstra.wonders.api.dto.request.StartRequest;
import com.jtriemstra.wonders.api.dto.request.UpdateGameRequest;
import com.jtriemstra.wonders.api.dto.request.WaitRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.dto.response.CreateJoinResponse;
import com.jtriemstra.wonders.api.dto.response.ListGameResponse;
import com.jtriemstra.wonders.api.dto.response.NeighborInfo;
import com.jtriemstra.wonders.api.dto.response.RefreshResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.GameList;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.UpdateGame;
import com.jtriemstra.wonders.api.model.action.Wait;
import com.jtriemstra.wonders.api.model.action.Wait.For;
import com.jtriemstra.wonders.api.model.board.BoardFactory;

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
	
	@Autowired
	/*@Qualifier("createNamedBoardFactory")*/
	private BoardFactory boardFactory;

	@WondersLogger
	@RequestMapping("/create")
	public CreateJoinResponse createGame(CreateRequest request) {
		Game game = gameFactory.createGame(request.getPlayerName(), boardFactory);

		Player p = playerFactory.createPlayer(request.getPlayerName()); 
		game.addPlayer(p);
		p.addNextAction(new Wait(For.PLAYERS));
		p.addNextAction(new UpdateGame());
		games.add(request.getPlayerName(), game);
		
		CreateJoinResponse r = new CreateJoinResponse();
		r.setNextActions(p.getNextAction());
		r.setBoardName(p.getBoardName());
		r.setBoardSide(p.getBoardSide());
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/join")
	public CreateJoinResponse joinGame(JoinRequest request) {
		//TODO: allow changing the board you get
		
		Player p = playerFactory.createPlayer(request.getPlayerName());
		games.get(request.getGameName()).addPlayer(p);
			
		CreateJoinResponse r = new CreateJoinResponse();
		r.setNextActions(p.getNextAction());
		r.setBoardName(p.getBoardName());
		r.setBoardSide(p.getBoardSide());
		r.setAge(1);
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/start")
	public ActionResponse startGame(StartRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/updateGame")
	public ActionResponse updateGame(UpdateGameRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		
		g.setNumberOfPlayers(request.getNumberOfPlayers());
		
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/listGames")
	public ListGameResponse listGames() {
		ListGameResponse r = new ListGameResponse();
		r.setGames(games);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/options")
	public ActionResponse options(OptionsRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
				
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/play")
	public ActionResponse play(PlayRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/playFree")
	public ActionResponse playFree(PlayFreeRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/build")
	public ActionResponse build(BuildRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/chooseScience")
	public ActionResponse chooseScience(ChooseScienceRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/chooseGuild")
	public ActionResponse chooseGuild(ChooseGuildRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		request.setOptionName(URLDecoder.decode(URLDecoder.decode(request.getOptionName())));
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/discard")
	public ActionResponse discard(DiscardRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}
	
	@WondersLogger
	@RequestMapping("/wait")
	public BaseResponse wait(WaitRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
		
	}

	@WondersLogger
	@RequestMapping("/refresh")
	public BaseResponse refresh(RefreshRequest request) {
		Game g = games.get(request.getGameName());
		Player p = null;
		
		if (g != null) {
			p = g.getPlayer(request.getPlayerId());
		}
		
		RefreshResponse r = new RefreshResponse();
		if (p != null) {
			r.setBoardName(p.getBoardName());
			r.setBoardSide(p.getBoardSide());
			r.setCardsOnBoard(p.getPlayedCards());
			r.setCoins(p.getCoins());
			r.setNextActions(p.getNextAction());
			r.setCards(p.getPlayableCards(g.getLeftOf(p), g.getRightOf(p)));
			r.setBuildState(p.getBuildState());	
			r.setPlayerFound(true);
			if (p.getOptions() != null) {
				r.setOptions(p.getOptions());
			}
			r.setLeftNeighbor(new NeighborInfo(g.getLeftOf(p)));
			r.setRightNeighbor(new NeighborInfo(g.getRightOf(p)));
			r.setAllDefeats(p.getNumberOfDefeats());
			r.setAllVictories(p.getVictories());
		}
		else {
			r.setPlayerFound(false);
			
			return r;
		}
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/getEndOfAge")
	public BaseResponse getEndOfAge(GetEndOfAgeRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/finishGame")
	public BaseResponse finishGame(GetEndOfGameRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/startAge")
	public BaseResponse startAge(StartAgeRequest request) {
		Game g = games.get(request.getGameName());
		Player p = g.getPlayer(request.getPlayerId());
		ActionResponse r = p.doAction(request, g);
		
		return r;
	}

	@WondersLogger
	@RequestMapping("/endGame")
	public BaseResponse endGame(BaseRequest request) {
		Game g = games.get(request.getGameName());
		games.remove(request.getGameName());
		
		return new BaseResponse();
	}
}
