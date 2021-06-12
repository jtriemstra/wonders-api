package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class CardChaining extends Rule {

	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		Player p = actionEvaluating.getPlayer();
		Card c = actionEvaluating.getCard();
		if (p.canPlayByChain(c.getName())) {
			return new PlayableBuildableResult(c, Status.OK, 0, 0, 0);
		}
		else {
			return getNextRule().evaluate(actionEvaluating);
		}
	}

	@Override
	public double getOrder() {
		return 2.0;
	}

}
