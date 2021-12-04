package com.jtriemstra.wonders.api.model.points;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;

public class ArmyPointStrategyAlexander implements PointStrategy {

	@Override
	public int getPoints(Player p) {
int result = 0;
		
		result += -1 * p.getArmyFacade().getNumberOfDefeats();
		
		for (List<Integer> l : p.getArmyFacade().getVictories().values()) {
			result += l.stream().mapToInt(Integer::intValue).map(i -> i + 1).sum();
		}
		return result;
	}

}
