package com.jtriemstra.wonders.api.model.points;

import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

//TODO: maybe I don't really need this
public class VictoryPointFacadeLeaders extends VictoryPointFacade {
	
	public VictoryPointFacadeLeaders() {
		super();
		this.pointCalculations.put(VictoryPointType.LEADER, new TypePointStrategy(VictoryPointType.LEADER));
	}
	
	public void replaceBasicStrategy(VictoryPointType type, PointStrategy strategy) {
		this.pointCalculations.replace(type, strategy);
	}
}
