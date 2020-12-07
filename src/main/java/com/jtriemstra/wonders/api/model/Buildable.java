package com.jtriemstra.wonders.api.model;

import com.jtriemstra.wonders.api.model.board.WonderStage;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;

import lombok.Data;

@Data
public class Buildable {
	private Status status;
	private int cost;
	private int leftCost;
	private int rightCost;
	private WonderStage stage;
	
	public Buildable(WonderStage stage, Status s, int cost, int leftCost, int rightCost) {
		status = s;
		this.cost = cost;
		this.leftCost = leftCost;
		this.rightCost = rightCost;
		this.stage = stage;
	}
	
}
