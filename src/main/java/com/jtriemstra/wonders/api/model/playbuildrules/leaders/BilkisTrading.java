package com.jtriemstra.wonders.api.model.playbuildrules.leaders;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.playbuildrules.Trading;
import com.jtriemstra.wonders.api.model.resource.EvalInfo;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;
import com.jtriemstra.wonders.api.model.resource.ResourceWithTradeCost;

public class BilkisTrading extends Trading {
	
	@Override
	protected List<EvalInfo> buildInitialResources() {
		List<EvalInfo> results = new ArrayList<>();
		List<ResourceWithTradeCost> resources = new ArrayList<>();
		resources.add(new ResourceWithTradeCost(new ResourceSet(ResourceType.BRICK, ResourceType.GLASS, ResourceType.ORE, ResourceType.PAPER, ResourceType.STONE, ResourceType.TEXTILE, ResourceType.WOOD), 1, "Bank"));
		results.add( new EvalInfo( resources, "Bank"));
		return results;
	}

	@Override
	public double getOrder() {
		return 6.9;
	}
}
