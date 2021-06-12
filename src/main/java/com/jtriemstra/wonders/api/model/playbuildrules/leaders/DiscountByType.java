package com.jtriemstra.wonders.api.model.playbuildrules.leaders;

import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildable;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.model.playbuildrules.Rule;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DiscountByType extends Rule {

	private Class cardClass;
	
	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		if (cardClass.isInstance(actionEvaluating.getCard())) {
			actionEvaluating.getUnusedResources().add(new ResourceSet(ResourceType.BRICK, ResourceType.GLASS, ResourceType.ORE, ResourceType.PAPER, ResourceType.STONE, ResourceType.TEXTILE, ResourceType.WOOD));
		}
		
		return getNextRule().evaluate(actionEvaluating);
	}

	@Override
	public double getOrder() {
		return 4.5;
	}

}
