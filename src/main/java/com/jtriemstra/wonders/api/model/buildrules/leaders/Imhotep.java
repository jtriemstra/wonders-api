package com.jtriemstra.wonders.api.model.buildrules.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.buildrules.BuildRule;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class Imhotep extends BuildRule {

	@Override
	public Buildable evaluate(WonderStage ws, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor) {
		unused.add(new ResourceSet(ResourceType.BRICK, ResourceType.GLASS, ResourceType.ORE, ResourceType.PAPER, ResourceType.STONE, ResourceType.TEXTILE, ResourceType.WOOD));
	
		return getNextRule().evaluate(ws, p, currentNeed, unused, null, null);
	}

	@Override
	public double getOrder() {
		return 4.5;
	}

}
