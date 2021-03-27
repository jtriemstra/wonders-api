package com.jtriemstra.wonders.api.model.points;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class TypePointStrategy implements PointStrategy {

	private VictoryPointType type;
	
	public TypePointStrategy(VictoryPointType type) {
		this.type = type;
	}

	@Override
	public int getPoints(Player p) {
		return p.getVictoryPoints().stream().filter(vpp -> vpp.getType() == type).mapToInt(VictoryPointProvider::getPoints).sum();
	}
	
	
}
