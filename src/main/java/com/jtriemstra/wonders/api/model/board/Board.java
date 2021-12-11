package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public abstract class Board {
	
	protected WonderStages stages;
	protected boolean sideA;
	
	public abstract ResourceSet getStartingResource();
	public abstract String getName();
	public abstract StartingBenefit getStartingBenefit();
	
	public Board(boolean sideA) {
		this.sideA = sideA;
	}
	
	public WonderStage getNextStage() {
		if (stages.hasNext()) {
			return stages.getNext();
		}
		return null;
	}
	
	public WonderStage build(IPlayer player, Game game) {
		// TODO: (low) use a cleaner indicator here
		return stages.build(player, game, game.getFlow().getCurrentAge() == 0 ? "leader" : Integer.toString(game.getFlow().getCurrentAge()));
	}

	public String[] getBuildState() {
		return stages.getBuildState();
	}
	public int getNumberOfBuiltStages() {
		return stages.getNumberOfBuiltStages();
	}
	public String getSide() {
		return sideA ? "A" : "B";
	}
	
	public interface StartingBenefit {
		public void claim(IPlayer p, Game g);
	}
}
