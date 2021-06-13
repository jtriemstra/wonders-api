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
		game.addPostTurnAction(player, new GetOptionsFromDiscard(1.6));
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 3;
	}

	@Override
	public String getHelp() {
		return "This card allows you to choose a card from the discard pile and play it for free";
	}
}
