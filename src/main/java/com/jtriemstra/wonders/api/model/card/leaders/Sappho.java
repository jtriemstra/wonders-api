package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Sappho extends LeaderCard {
	
	@Override
	public String getName() {
		return "Sappho";
	}

	@Override
	public void play(Player player, Game game) {
		player.addVPProvider(new SimpleVPProvider(2, VictoryPointType.LEADER));
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}
}
