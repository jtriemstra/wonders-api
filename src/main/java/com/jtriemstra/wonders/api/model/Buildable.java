package com.jtriemstra.wonders.api.model;

import java.util.List;

import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

import lombok.Data;

@Data
public class Buildable {
	private Status status;
	private int cost;
	private int leftCost;
	private int rightCost;
	private WonderStage stage;
	private List<TradeCost> costOptions;
	
	public Buildable(WonderStage stage, Status s, int cost, int leftCost, int rightCost) {
		status = s;
		this.cost = cost;
		this.leftCost = leftCost;
		this.rightCost = rightCost;
		this.stage = stage;
	}

	public Buildable(WonderStage stage, Status s, List<TradeCost> costs) {
		status = s;
		this.stage = stage;
		this.costOptions = costs;
	}
}
