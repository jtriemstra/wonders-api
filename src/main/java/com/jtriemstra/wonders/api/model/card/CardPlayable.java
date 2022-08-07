package com.jtriemstra.wonders.api.model.card;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult.PaymentFunction;
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
	private int bankCost;
	private List<TradeCost> costOptions;
	private PaymentFunction paymentFunction;
	
	public CardPlayable(Card c, Status s, List<TradeCost> costs, int bankCost) {
		status = s;
		card = c;
		this.bankCost = bankCost;
		this.costOptions = costs;
	}	
	
	@JsonIgnore
	public PaymentFunction getPaymentFunction() {
		return paymentFunction;
	}
}
