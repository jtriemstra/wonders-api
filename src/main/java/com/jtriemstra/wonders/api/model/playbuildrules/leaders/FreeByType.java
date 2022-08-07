package com.jtriemstra.wonders.api.model.playbuildrules.leaders;

import java.util.ArrayList;

import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildable;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.model.playbuildrules.Rule;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FreeByType extends Rule {

	private Class cardClass;
	
	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		if (cardClass.isInstance(actionEvaluating.getCard())) {
			return new PlayableBuildableResult(actionEvaluating.getCard(), Status.OK, new ArrayList<>());
		}
		else {
			return getNextRule().evaluate(actionEvaluating);
		}
	}

	@Override
	public double getOrder() {
		return 1.5;
	}

}
