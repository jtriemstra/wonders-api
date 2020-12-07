package com.jtriemstra.wonders.api.model.card.provider;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleVPProvider implements VictoryPointProvider {
	private int victoryPoints;
	private VictoryPointType type;

	@Override
	public int getPoints() {
		return victoryPoints;
	}	

	@Override
	public VictoryPointType getType() {
		return type;
	}
	
	
}
