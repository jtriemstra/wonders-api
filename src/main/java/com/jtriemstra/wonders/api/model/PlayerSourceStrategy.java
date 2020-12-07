package com.jtriemstra.wonders.api.model;

import java.util.List;

public interface PlayerSourceStrategy {
	public List<Player> getPlayers(Player p, Game g);
}
