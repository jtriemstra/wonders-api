package com.jtriemstra.wonders.api.model.playbuildrules.leaders;

import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildable;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.model.playbuildrules.Rule;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CoinDiscountByType extends Rule {

	private Class cardClass;
	private int discount;
	
	@Override
	public PlayableBuildableResult evaluate(PlayableBuildable actionEvaluating) {
		if (cardClass.isInstance(actionEvaluating.getCard())) {
			actionEvaluating.discountCoinCost(discount);
		}
		
		return getNextRule().evaluate(actionEvaluating);		
	}

	@Override
	public double getOrder() {
		return 2.9;
	}

}
