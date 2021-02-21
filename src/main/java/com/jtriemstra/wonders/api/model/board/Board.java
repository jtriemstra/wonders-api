package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public abstract class Board {
	
	public static final int EPHESUS_ID=0;
	public static final int OLYMPUS_ID=1;
	public static final int HALIKARNASSOS_ID=2;
	public static final int GIZA_ID=3;
	public static final int RHODES_ID=4;
	public static final int ALEXANDRIA_ID=5;
	public static final int BABYLON_ID=6;
	
	protected WonderStages stages;
	protected boolean sideA;
	
	public abstract ResourceSet getStartingResource();
	public abstract String getName();
	public abstract int getID();
	
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
