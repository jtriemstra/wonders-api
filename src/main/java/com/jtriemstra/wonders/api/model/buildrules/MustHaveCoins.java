package com.jtriemstra.wonders.api.model.buildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class MustHaveCoins extends BuildRule {

	@Override
	public Buildable evaluate(WonderStage ws, Player p, ResourceCost currentNeed, List<ResourceSet> unused, Player leftNeighbor, Player rightNeighbor) {
		if (ws.getCoinCost() > p.getCoins()) {
			return new Buildable(null, Status.ERR_COINS, 0, 0, 0);
		}
		else {
			return getNextRule().evaluate(ws, p, currentNeed, unused, leftNeighbor, rightNeighbor);
		}
	}

	@Override
	public double getOrder() {
		return 3.0;
	}

}
