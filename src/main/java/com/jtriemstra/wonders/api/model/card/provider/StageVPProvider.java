package com.jtriemstra.wonders.api.model.card.provider;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;

public class StageVPProvider implements VictoryPointProvider {
	private int pointsPer;
	private List<Player> players;
	private VictoryPointType type;
	
	public StageVPProvider(int pointsPer, List<Player> players, VictoryPointType type) {
		this.pointsPer = pointsPer;
		this.players = players;
		this.type = type;
	}

	@Override
	public int getPoints() {
		int points = 0;
		for (Player p : players) {
			points += pointsPer * p.getNumberOfBuiltStages();
		}
		return points;
	}

	@Override
	public VictoryPointType getType() {
		return type;
	}

}
