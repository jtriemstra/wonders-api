package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.points.ArmyPointStrategyAlexander;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacade;

public class Alexander extends LeaderCard {

	@Override
	public String getName() {
		return "Alexander";
	}

	@Override
	public void play(IPlayer player, Game game) {
		VictoryPointFacade pointCalculations = player.getPointCalculations();
		pointCalculations.replaceBasicStrategy(VictoryPointType.ARMY, new ArmyPointStrategyAlexander());
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 3;
	}

	@Override
	public String getHelp() {
		return "This card changes your conflict victory points at the end of each age from 1, 3, and 5 points to 2, 4, and 6"; 
	}
}
