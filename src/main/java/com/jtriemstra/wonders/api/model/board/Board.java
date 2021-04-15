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
	public static final int ROME_ID=7;
	
	protected WonderStages stages;
	protected boolean sideA;
	
	public abstract ResourceSet getStartingResource();
	public abstract String getName();
	public abstract int getID();
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
		
	public static String getName(int id) {
		switch(id) {
		case EPHESUS_ID:return "Ephesus"; 
		case OLYMPUS_ID: return "Olympia";
		case HALIKARNASSOS_ID: return "Halikarnassos";
		case GIZA_ID: return "Giza";
		case BABYLON_ID: return "Babylon";
		case RHODES_ID: return "Rhodes";
		case ALEXANDRIA_ID: return "Alexandria";
		}
		throw new RuntimeException("board id not found");
	}
	public static int getId(String boardName) {
		switch(boardName) {
		case "Ephesus":return EPHESUS_ID; 
		case "Olympia": return OLYMPUS_ID;
		case "Halikarnassos": return HALIKARNASSOS_ID;
		case "Giza": return GIZA_ID;
		case "Babylon": return BABYLON_ID;
		case "Rhodes": return RHODES_ID;
		case "Alexandria": return ALEXANDRIA_ID;
		}
		throw new RuntimeException("board name not found");
	}
}
