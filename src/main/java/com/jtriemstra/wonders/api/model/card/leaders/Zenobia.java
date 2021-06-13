package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Zenobia extends LeaderCard {
	
	@Override
	public String getName() {
		return "Zenobia";
	}

	@Override
	public void play(Player player, Game game) {
		player.addVPProvider(new SimpleVPProvider(3, VictoryPointType.LEADER));
	}
	
	@Override
	public int getCoinCost() {
		return 2;
	}

	@Override
	public String getHelp() {
		return "This card grants 3 victory points";
	}
}
