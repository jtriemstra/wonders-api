package com.jtriemstra.wonders.api.model;

import java.util.List;

import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

import lombok.Data;

@Data
public class Buildable {
	private Status status;
	private WonderStage stage;
	private List<TradeCost> costOptions;
	
	public Buildable(WonderStage stage, Status s, List<TradeCost> costs) {
		status = s;
		this.stage = stage;
		this.costOptions = costs;
	}
}
