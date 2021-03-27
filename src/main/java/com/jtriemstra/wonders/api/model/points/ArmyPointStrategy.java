package com.jtriemstra.wonders.api.model.points;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;

public class ArmyPointStrategy implements PointStrategy {

	@Override
	public int getPoints(Player p) {
		int result = 0;
		
		result += -1 * p.getNumberOfDefeats();
		
		for (List<Integer> l : p.getVictories().values()) {
			result += l.stream().mapToInt(Integer::intValue).sum();
		}
		return result;
	}

}
