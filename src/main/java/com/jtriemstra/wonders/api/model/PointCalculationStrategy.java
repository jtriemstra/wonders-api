package com.jtriemstra.wonders.api.model;

import com.jtriemstra.wonders.api.model.points.VictoryPointFacade;

public interface PointCalculationStrategy {
	public VictoryPointFacade getPointFacade();
}
