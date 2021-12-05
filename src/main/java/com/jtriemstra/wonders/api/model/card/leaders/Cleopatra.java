package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Cleopatra extends LeaderCard {
	
	@Override
	public String getName() {
		return "Cleopatra";
	}

	@Override
	public void play(IPlayer player, Game game) {
		player.addVPProvider(new SimpleVPProvider(5, VictoryPointType.LEADER));
	}
	
	@Override
	public int getCoinCost() {
		return 4;
	}

	@Override
	public String getHelp() {
		return "This card grants 5 victory points";
	}

	@Override
	public String getType() {
		return "victory";
	}
}
