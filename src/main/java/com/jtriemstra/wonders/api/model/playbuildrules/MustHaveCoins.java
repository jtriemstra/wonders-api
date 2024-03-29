package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class MustHaveCoins extends Rule {

	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		if (actionEvaluating.getCoinCost() > actionEvaluating.getAvailableCoins()) {
			return new PlayableBuildableResult(actionEvaluating, Status.ERR_COINS, new ArrayList<>());
		}
		else {
			return getNextRule().evaluate(actionEvaluating);
		}
	}

	@Override
	public double getOrder() {
		return 3.0;
	}

}
