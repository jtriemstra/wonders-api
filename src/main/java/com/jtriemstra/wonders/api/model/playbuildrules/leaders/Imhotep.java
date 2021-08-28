package com.jtriemstra.wonders.api.model.playbuildrules.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildable;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.model.playbuildrules.Rule;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Imhotep extends Rule {

	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		
		List<ResourceSet> unused = actionEvaluating.getUnusedResources();
		
		if (actionEvaluating.getCard() == null && actionEvaluating.getStage() != null) {
			unused.add(new ResourceSet(ResourceType.BRICK, ResourceType.GLASS, ResourceType.ORE, ResourceType.PAPER, ResourceType.STONE, ResourceType.TEXTILE, ResourceType.WOOD));
		}
		
		return getNextRule().evaluate(actionEvaluating);
	}

	@Override
	public double getOrder() {
		return 4.5;
	}

}
