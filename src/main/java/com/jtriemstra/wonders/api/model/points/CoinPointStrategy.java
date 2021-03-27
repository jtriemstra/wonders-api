package com.jtriemstra.wonders.api.model.points;

import com.jtriemstra.wonders.api.model.Player;

public class CoinPointStrategy implements PointStrategy{

	@Override
	public int getPoints(Player p) {
		return p.getCoins() / 3;
	}

}
