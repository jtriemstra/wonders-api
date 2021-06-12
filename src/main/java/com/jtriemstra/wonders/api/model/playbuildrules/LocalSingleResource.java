package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class LocalSingleResource extends Rule {

	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		List<ResourceSet> unused = actionEvaluating.getUnusedResources();
		ResourceCost currentNeed = actionEvaluating.getResourceCost();
		
		for (int i=0; i<unused.size();) {
			if (unused.get(i).isSingle()) {
				ResourceType currentAvailableType = unused.get(i).getSingle();
				if (currentNeed.isNeeded(currentAvailableType)) {
					currentNeed.reduce(currentAvailableType);
				}
				unused.remove(i);
			}
			else if (!currentNeed.isNeeded(unused.get(i))) {
				unused.remove(i);
			}
			else {
				i++;
			}
			
			if (currentNeed.isComplete()) {
				return new PlayableBuildableResult(actionEvaluating, Status.OK, actionEvaluating.getCoinCost(), 0, 0);
			}
		}
		
		return getNextRule().evaluate(actionEvaluating);
	}

	@Override
	public double getOrder() {
		return 5.0;
	}

}
