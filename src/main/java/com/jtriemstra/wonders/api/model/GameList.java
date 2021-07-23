package com.jtriemstra.wonders.api.model;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class GameList {

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
