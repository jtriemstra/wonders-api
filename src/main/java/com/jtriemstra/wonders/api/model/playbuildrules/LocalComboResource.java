package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.LocalResourceEvaluator;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class LocalComboResource extends Rule {

	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		List<ResourceSet> unused = actionEvaluating.getUnusedResources();
		ResourceCost currentNeed = actionEvaluating.getResourceCost();
		
		LocalResourceEvaluator eval = new LocalResourceEvaluator(unused);
		if (eval.test(currentNeed)) {
			return new PlayableBuildableResult(actionEvaluating, Status.OK, new ArrayList<>(), actionEvaluating.getCoinCost());
		}
		
		return getNextRule().evaluate(actionEvaluating);
	}

	@Override
	public double getOrder() {
		return 6.0;
	}

}
