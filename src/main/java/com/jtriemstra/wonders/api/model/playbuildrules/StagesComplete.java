package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Buildable;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;

public class StagesComplete extends Rule {
	
	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		if (actionEvaluating.getStage() == null) {
			return new PlayableBuildableResult((WonderStage) null, Status.ERR_FINISHED, 0, 0, 0);
		}
		else {
			return getNextRule().evaluate(actionEvaluating);
		}
	}

	@Override
	public double getOrder() {
		return 1.0;
	}

}
