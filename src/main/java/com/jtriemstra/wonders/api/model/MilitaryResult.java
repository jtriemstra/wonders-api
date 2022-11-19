package com.jtriemstra.wonders.api.model;

import lombok.Data;

@Data
public class MilitaryResult {
	private boolean left;
	private String neighborName;
	private int points;
	
	public MilitaryResult(boolean isLeft, String neighborName, int points) {
		this.left = isLeft;
		this.neighborName = neighborName;
		this.points = points;
	}
}