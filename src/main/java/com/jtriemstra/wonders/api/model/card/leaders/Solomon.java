package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsFromDiscard;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.points.ArmyPointStrategyAlexander;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacadeLeaders;

public class Solomon extends LeaderCard {

	@Override
	public String getName() {
		return "Solomon";
	}

	@Override
	public void play(Player player, Game game) {
		//TODO: test ordering with Halikarnassos
		game.addPostTurnAction(player, new GetOptionsFromDiscard(1.6));
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 3;
	}
}
