package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.points.ArmyPointStrategyAlexander;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacade;

public class Alexander extends LeaderCard {

	@Override
	public String getName() {
		return "Alexander";
	}

	@Override
	public void play(Player player, Game game) {
		VictoryPointFacade pointCalculations = player.getPointCalculations();
		pointCalculations.replaceBasicStrategy(VictoryPointType.ARMY, new ArmyPointStrategyAlexander());
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}
}
