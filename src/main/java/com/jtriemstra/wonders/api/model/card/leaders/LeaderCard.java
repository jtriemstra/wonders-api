package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;

public abstract class LeaderCard extends Card {

	public LeaderCard() {
		super(3, -1);
	}

	@Override
	public String getType() {
		return "leader";
	}
	
	@Override
	public void play(IPlayer player, Game game) {
		super.play(player, game);
	}
}
