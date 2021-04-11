package com.jtriemstra.wonders.api.model.buildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class BuildableRuleChain {

	private BuildRule first;
	
	public BuildableRuleChain() {
		first = new StagesComplete();
		first.insert(new MustHaveCoins());
		first.insert(new StageWithoutCost());
		first.insert(new LocalSingleResource());
		first.insert(new LocalComboResource());
		first.insert(new Trading());
		first.insert(new CantBuild());
	}
	
	public Buildable evaluate(WonderStage ws, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor) {
		return first.evaluate(ws, p, currentNeed, unused, leftNeighbor, rightNeighbor);
	}
	
	public void addRule(BuildRule pr) {
		first.insert(pr);
	}
}
