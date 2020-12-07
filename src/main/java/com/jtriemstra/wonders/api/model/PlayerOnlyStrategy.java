package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerOnlyStrategy implements PlayerSourceStrategy {

	@Override
	public List<Player> getPlayers(Player p, Game g) {
		List<Player> result = new ArrayList<>();
		result.add(p);
		
		return result;
	}

}
