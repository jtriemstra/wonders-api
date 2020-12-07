package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Altar extends VictoryCard {

	public Altar(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Altar";
	}
	
	@Override
	public VictoryPointProvider getVPProvider() {
		return new SimpleVPProvider(2, VictoryPointType.VICTORY);
	}

	@Override
	public String[] getFreebies() {
		return new String[] {"Temple"};
	}
}
