package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.points.SciencePointStrategyAristotle;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacadeLeaders;

public class Aristotle extends LeaderCard {

	@Override
	public String getName() {
		return "Aristotle";
	}

	@Override
	public void play(Player player, Game game) {
		//VictoryPointFacadeLeaders pointCalculations = (VictoryPointFacadeLeaders) player.getPointCalculations();
		player.getPointCalculations().replaceBasicStrategy(VictoryPointType.SCIENCE, new SciencePointStrategyAristotle());
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 3;
	}
}
