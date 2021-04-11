package com.jtriemstra.wonders.api.model.buildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.LocalResourceEvaluator;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class LocalComboResource extends BuildRule {

	@Override
	public Buildable evaluate(WonderStage ws, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor) {
		LocalResourceEvaluator eval = new LocalResourceEvaluator(unused);
		if (eval.test(currentNeed)) {
			return new Buildable(ws, Status.OK, 0, 0, 0);
		}
		
		return getNextRule().evaluate(ws, p, currentNeed, unused, leftNeighbor, rightNeighbor);
	}

	@Override
	public double getOrder() {
		return 6.0;
	}

}
