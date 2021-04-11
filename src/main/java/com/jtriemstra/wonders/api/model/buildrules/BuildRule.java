package com.jtriemstra.wonders.api.model.buildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

import lombok.Getter;
import lombok.Setter;

public abstract class BuildRule {
	@Getter @Setter
	private BuildRule nextRule;
	
	public abstract Buildable evaluate(WonderStage ws, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor);
	
	public abstract double getOrder();
	
	private void insertAfter(BuildRule newRule) {
		newRule.setNextRule(nextRule);
		nextRule = newRule;
	}
	
	public void insert(BuildRule newRule) {
		if (nextRule == null || newRule.getOrder() < nextRule.getOrder()) {
			insertAfter(newRule);
		}
		else {
			nextRule.insert(newRule);
		}
	}
}
