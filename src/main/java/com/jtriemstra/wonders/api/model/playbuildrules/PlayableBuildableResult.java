package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

import lombok.Data;

@Data
public class PlayableBuildableResult {
	private Status status;
	private int cost;
	private int leftCost;
	private int rightCost;
	private WonderStage stage;
	private Card card;
	private List<TradeCost> costOptions;
		

	public PlayableBuildableResult(PlayableBuildable actionEvaluating, Status s, int bankCost, int leftCost, int rightCost) {
		status = s;
		this.cost = bankCost;
		this.leftCost = leftCost;
		this.rightCost = rightCost;
		this.stage = actionEvaluating.getStage();
		this.card = actionEvaluating.getCard();
	}
	
	public PlayableBuildableResult(WonderStage stage, Status s, int bankCost, int leftCost, int rightCost) {
		status = s;
		this.cost = bankCost;
		this.leftCost = leftCost;
		this.rightCost = rightCost;
		this.stage = stage;
	}

	public PlayableBuildableResult(Card card, Status s, int bankCost, int leftCost, int rightCost) {
		status = s;
		this.cost = bankCost;
		this.leftCost = leftCost;
		this.rightCost = rightCost;
		this.card = card;
	}
	
	public PlayableBuildableResult(WonderStage stage, Status s, List<TradeCost> costs) {
		status = s;
		this.stage = stage;
		this.costOptions = costs;
	}

	public PlayableBuildableResult(Card card, Status s, List<TradeCost> costs) {
		status = s;
		this.card = card;
		this.costOptions = costs;
	}

	public PlayableBuildableResult(PlayableBuildable actionEvaluating, Status s, List<TradeCost> costs) {
		status = s;
		this.card = actionEvaluating.getCard();
		this.stage = actionEvaluating.getStage();
		this.costOptions = costs;
	}

	public PlayableBuildableResult(PlayableBuildable actionEvaluating, Status s, List<TradeCost> costs, int bankCost) {
		status = s;
		this.card = actionEvaluating.getCard();
		this.stage = actionEvaluating.getStage();
		this.costOptions = costs;
		this.cost = bankCost;
	}
}
