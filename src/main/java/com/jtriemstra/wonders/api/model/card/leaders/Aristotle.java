package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.points.SciencePointStrategyAristotle;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacadeLeaders;

public class Aristotle extends LeaderCard {

	@Override
	public String getName() {
		return "Aristotle";
	}

	@Override
	public void play(IPlayer player, Game game) {
		player.getPointCalculations().replaceBasicStrategy(VictoryPointType.SCIENCE, new SciencePointStrategyAristotle());
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 3;
	}

	@Override
	public String getHelp() {
		return "This card grants 10 points for every set of three distinct green card symbols, rather than usual 7 points";
	}
}
