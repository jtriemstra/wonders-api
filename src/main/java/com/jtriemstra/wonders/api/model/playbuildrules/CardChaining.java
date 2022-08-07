package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class CardChaining extends Rule {

	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		IPlayer p = actionEvaluating.getPlayer();
		Card c = actionEvaluating.getCard();
		if (p.canPlayByChain(c.getName())) {
			PlayableBuildableResult result = new PlayableBuildableResult(c, Status.OK, new ArrayList<>());
			result.setPaymentFunction( (player, game) -> player.eventNotify("play.free"));
			return result;
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
