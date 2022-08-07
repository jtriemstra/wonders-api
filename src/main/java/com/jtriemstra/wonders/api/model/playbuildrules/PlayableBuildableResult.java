package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

import lombok.Data;

@Data
public class PlayableBuildableResult {
	private Status status;
	private int cost;
	private WonderStage stage;
	private Card card;
	private List<TradeCost> costOptions;
	private PaymentFunction paymentFunction;
	
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
	
	public interface PaymentFunction {
		public void pay(IPlayer p, Game g);
	}
}
