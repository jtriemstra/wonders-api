package com.jtriemstra.wonders.api.model.card;

import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Theater extends VictoryCard {

	public Theater(int minPlayers, int age) {
		super(minPlayers, age);
	}
	
	@Override
	public String getName() {
		return "Theater";
	}
	
	@Override
	public SimpleVPProvider getVPProvider() {
		return new SimpleVPProvider(2, VictoryPointType.VICTORY);
	}

	@Override
	public String[] getFreebies() {
		return new String[] {"Statue"};
	}
}
