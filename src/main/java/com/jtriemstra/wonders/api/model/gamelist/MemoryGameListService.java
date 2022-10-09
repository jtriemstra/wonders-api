package com.jtriemstra.wonders.api.model.gamelist;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jtriemstra.wonders.api.model.Game;

@Service
public class MemoryGameListService implements GameListService {

	private HashMap<String, Game> games = new HashMap<>();
	
	public Set<String> getGames() {
		return games.values().stream().map(Game::getName).collect(Collectors.toSet());
	}

	public void add(String playerName, Game game) {
		games.put(playerName, game);
	}

	public Game get(String gameName) {
		return games.get(gameName);
	}

	public void remove(String gameName) {
		games.remove(gameName);
	}
	
}
