package com.jtriemstra.wonders.api.model.card.provider;

import java.util.List;

import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;

public class LambdaVPProvider implements VictoryPointProvider {
	private int pointsPer;
	private List<IPlayer> players;
	private VictoryPointLambda fn;
	private VictoryPointType type;
	
	public LambdaVPProvider(int pointsPer, List<IPlayer> players, VictoryPointLambda fn, VictoryPointType type) {
		this.pointsPer = pointsPer;
		this.players = players;
		this.fn = fn;
		this.type = type;
	}
	
	@Override
	public int getPoints() {
		int points = 0;
		for (IPlayer p : players) {
			points += pointsPer * fn.countForPlayer(p);
		}
		return points;
	}

	@Override
	public VictoryPointType getType() {
		return type;
	}

}
