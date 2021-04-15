package com.jtriemstra.wonders.api.model.playrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

import lombok.Getter;
import lombok.Setter;

public abstract class PlayRule {
	@Getter @Setter
	private PlayRule nextRule;
	
	public abstract CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor, int coinDiscount);
	
	public abstract double getOrder();
	
	private void insertAfter(PlayRule newRule) {
		newRule.setNextRule(nextRule);
		nextRule = newRule;
	}
	
	public void insert(PlayRule newRule) {
		if (nextRule == null || newRule.getOrder() < nextRule.getOrder()) {
			insertAfter(newRule);
		}
		else {
			nextRule.insert(newRule);
		}
	}
}
