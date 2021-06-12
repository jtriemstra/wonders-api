package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class CantExecute extends Rule {

	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		return new PlayableBuildableResult(actionEvaluating, Status.ERR_RESOURCE, 0, 0, 0);
	}

	@Override
	public double getOrder() {
		return 10.0;
	}

}
