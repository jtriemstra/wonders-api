package com.jtriemstra.wonders.api.model.gamelist;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.state.StateService;

@Service
public class MemoryGameListService implements GameListService {

	private HashMap<String, Game> games = new HashMap<>();
	
	@Autowired
	private StateService stateService;
	
	public MemoryGameListService(StateService stateService) {
		this.stateService = stateService;
	}
	
	public Set<String> getGames() {
		return games.values().stream().map(Game::getName).collect(Collectors.toSet());
	}

	public void add(String playerName, Game game) {
		games.put(playerName, game);
		stateService.createGame(playerName);
	}

	public Game get(String gameName) {
		return games.get(gameName);
	}

	public void remove(String gameName) {
		games.remove(gameName);
	}
	
}
