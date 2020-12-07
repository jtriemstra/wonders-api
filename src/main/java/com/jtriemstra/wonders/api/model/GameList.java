package com.jtriemstra.wonders.api.model;

import java.util.HashMap;
import java.util.Set;

public class GameList {

private HashMap<String, Game> games = new HashMap<>();
	
	public Set<String> getGames() {
		return games.keySet();
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
