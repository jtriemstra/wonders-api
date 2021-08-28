package com.jtriemstra.wonders.api.model.board;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

public class WonderStages {

	private List<WonderStage> stages;
	private int currentIndex = 0;
	private String[] buildState;
	
	public WonderStages(WonderStage... stages) {
		this.stages = new ArrayList<WonderStage>();
		for (WonderStage s : stages) {
			this.stages.add(s);
		}
		buildState = new String[this.stages.size()];
	}
	
	public WonderStage build(Player player, Game game, String currentAge) {
		WonderStage thisStage = stages.get(currentIndex);
		thisStage.build(player, game);
		buildState[currentIndex] = currentAge;
		currentIndex++;
		
		return thisStage;
	}
	
	public boolean hasNext() {
		return currentIndex < stages.size();
	}
	
	public WonderStage getNext() {
		return stages.get(currentIndex);
	}

	public String[] getBuildState() {
		return buildState;
	}

	public int getNumberOfBuiltStages() {
		return currentIndex;		
	}

}
