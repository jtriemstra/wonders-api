package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.ArrayList;

import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;

public class ActionWithoutCost extends Rule {

	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		if (actionEvaluating.getResourceCost() == null) {
			return new PlayableBuildableResult(actionEvaluating, Status.OK, new ArrayList<>(), actionEvaluating.getCoinCost());
		}
		else {
			return getNextRule().evaluate(actionEvaluating);
		}
	}

	@Override
	public double getOrder() {
		return 4.0;
	}

}
