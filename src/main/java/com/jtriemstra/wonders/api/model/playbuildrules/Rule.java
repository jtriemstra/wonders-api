package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

import lombok.Getter;
import lombok.Setter;

public abstract class Rule {
	@Getter @Setter
	private Rule nextRule;
	
	public abstract PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating);
	
	public abstract double getOrder();
	
	private void insertAfter(Rule newRule) {
		newRule.setNextRule(nextRule);
		nextRule = newRule;
	}
	
	public void insert(Rule newRule) {
		if (nextRule == null || newRule.getOrder() < nextRule.getOrder()) {
			insertAfter(newRule);
		}
		else {
			nextRule.insert(newRule);
		}
	}
}
