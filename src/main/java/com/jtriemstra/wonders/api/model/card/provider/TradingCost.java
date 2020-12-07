package com.jtriemstra.wonders.api.model.card.provider;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradingCost {
	private int leftCost;
	private int rightCost;
}
