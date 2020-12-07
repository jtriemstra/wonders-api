package com.jtriemstra.wonders.api.model.card.provider;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;

public class CardVPProvider implements VictoryPointProvider {
	private Class targetType;
	private int pointsPer;
	private List<Player> targets;
	private VictoryPointType type;
	
	public CardVPProvider(int pointsPer, Class clazz, List<Player> targets, VictoryPointType type) {
		this.targetType = clazz;
		this.pointsPer = pointsPer;
		this.targets = targets;
		this.type = type;
	}

	@Override
	public int getPoints() {
		int point = 0;
		for (Player p : targets) {
			point += pointsPer * p.getCardsOfTypeFromBoard(targetType).size();
		}
		return point;
	}

	@Override
	public VictoryPointType getType() {
		return type;
	}
}
