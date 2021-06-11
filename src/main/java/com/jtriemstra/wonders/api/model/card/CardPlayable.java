package com.jtriemstra.wonders.api.model.card;

import java.util.List;

import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

import lombok.Data;

@Data
public class CardPlayable {
	public enum Status {
		OK,
		ERR_COINS,
		ERR_RESOURCE,
		ERR_DUPLICATE, 
		ERR_FINISHED
	}
		
	private Status status;
	private Card card;
	private int cost;
	private int leftCost;
	private int rightCost;
	private int bankCost;
	private List<TradeCost> costOptions;
	
	public CardPlayable(Card c, Status s, int cost, int leftCost, int rightCost) {
		status = s;
		card = c;
		this.cost = cost;
		this.leftCost = leftCost;
		this.rightCost = rightCost;
		this.bankCost = c.getCoinCost();
	}
	
	public CardPlayable(Card c, Status s, int cost, int leftCost, int rightCost, int bankCost) {
		status = s;
		card = c;
		this.cost = (cost == 0 ? bankCost : cost);
		this.leftCost = leftCost;
		this.rightCost = rightCost;
		this.bankCost = bankCost;
	}

	public CardPlayable(Card c, Status s, List<TradeCost> costs, int bankCost) {
		status = s;
		card = c;
		this.bankCost = bankCost;
		this.costOptions = costs;
	}	
}
