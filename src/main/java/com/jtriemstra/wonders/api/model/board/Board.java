package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public abstract class Board {
	
	protected WonderStages stages;
	protected boolean sideA;
	
	public abstract ResourceSet getStartingResource();
	public abstract String getName();
	public abstract void addStartingBenefit(Player player, Game game);
	
	public Board(boolean sideA) {
		this.sideA = sideA;
	}
	
	public WonderStage getNextStage() {
		if (stages.hasNext()) {
			return stages.getNext();
		}
		return null;
	}
	
	public WonderStage build(Player player, Game game) {
		return stages.build(player, game, game.getCurrentAge());
	}

	public int[] getBuildState() {
		return stages.getBuildState();
	}
	public int getNumberOfBuiltStages() {
		return stages.getNumberOfBuiltStages();
	}
	public String getSide() {
		return sideA ? "A" : "B";
	}
}
