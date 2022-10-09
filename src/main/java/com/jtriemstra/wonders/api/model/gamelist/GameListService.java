package com.jtriemstra.wonders.api.model.gamelist;

import java.util.Set;

import com.jtriemstra.wonders.api.model.Game;

public interface GameListService {
	public Set<String> getGames();

	public void add(String playerName, Game game);

	public Game get(String gameName);

	public void remove(String gameName);
	
}