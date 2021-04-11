package com.jtriemstra.wonders.api.model.buildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class LocalSingleResource extends BuildRule {

	@Override
	public Buildable evaluate(WonderStage ws, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor) {
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
				return new Buildable(ws, Status.OK, 0, 0, 0);
			}
		}
		
		return getNextRule().evaluate(ws, p, currentNeed, unused, leftNeighbor, rightNeighbor);
	}

	@Override
	public double getOrder() {
		return 5.0;
	}

}
