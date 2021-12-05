package com.jtriemstra.wonders.api.model;

import java.util.List;

public interface PlayerSourceStrategy {
	public List<IPlayer> getPlayers(IPlayer p, Game g);
}
