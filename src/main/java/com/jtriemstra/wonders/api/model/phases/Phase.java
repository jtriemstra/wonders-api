package com.jtriemstra.wonders.api.model.phases;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Phase {
	private Double order;
	private ActionFactory action;//TODO: can I get rid of actions?
	private GamePhaseStart startFunction;
	private int maxLoops;
	private int currentLoop;
}
