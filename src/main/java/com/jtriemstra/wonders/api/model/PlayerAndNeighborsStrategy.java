package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerAndNeighborsStrategy implements PlayerSourceStrategy {

	@Override
	public List<IPlayer> getPlayers(IPlayer p, Game g) {
		List<IPlayer> result = new ArrayList<>();
		result.add(p);
		result.add(g.getLeftOf(p));
		result.add(g.getRightOf(p));
		
		return result;
	}

}
