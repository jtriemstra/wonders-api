package com.jtriemstra.wonders.api.model.card.provider;

import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;

public interface VictoryPointLambda {
	public int countForPlayer(IPlayer p);
}
