package com.jtriemstra.wonders.api.model.playrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class PlayableRuleChain {

	private PlayRule first;
	
	public PlayableRuleChain() {
		first = new NoDuplicates();
		first.insert(new CardChaining());
		first.insert(new MustHaveCoins());
		first.insert(new CardsWithoutCost());
		first.insert(new LocalSingleResource());
		first.insert(new LocalComboResource());
		first.insert(new Trading());
		first.insert(new CantPlay());
	}
	
	public CardPlayable evaluate(Card c, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor) {
		return first.evaluate(c, p, currentNeed, unused, leftNeighbor, rightNeighbor);
	}
	
	public void addRule(PlayRule pr) {
		first.insert(pr);
	}
}
