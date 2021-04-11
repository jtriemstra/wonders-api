package com.jtriemstra.wonders.api.model.points;

import java.util.HashMap;
import java.util.Map;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class VictoryPointFacade {

	protected HashMap<VictoryPointType, PointStrategy> pointCalculations;
	
	public VictoryPointFacade() {
		pointCalculations = new HashMap<>();
		pointCalculations.put(VictoryPointType.ARMY, new ArmyPointStrategy());
		pointCalculations.put(VictoryPointType.COIN, new CoinPointStrategy());
		pointCalculations.put(VictoryPointType.COMMERCE, new TypePointStrategy(VictoryPointType.COMMERCE));
		pointCalculations.put(VictoryPointType.GUILD, new TypePointStrategy(VictoryPointType.GUILD));
		pointCalculations.put(VictoryPointType.STAGES, new TypePointStrategy(VictoryPointType.STAGES));
		pointCalculations.put(VictoryPointType.VICTORY, new TypePointStrategy(VictoryPointType.VICTORY));
		pointCalculations.put(VictoryPointType.SCIENCE, new SciencePointStrategy());
	}
	
	public Map<VictoryPointType, Integer> getFinalVictoryPoints(Player p) {
		Map<VictoryPointType, Integer> result = new HashMap<>();
		for (VictoryPointType type : pointCalculations.keySet()) {
			result.put(type, pointCalculations.get(type).getPoints(p));
		}
		return result;
	}

	public void replaceBasicStrategy(VictoryPointType type, PointStrategy strategy) {
		this.pointCalculations.replace(type, strategy);
	}
}
