package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.action.BaseAction;
import com.jtriemstra.wonders.api.model.action.ListBoards;

public class GamePhaseFactoryBoard extends GamePhaseFactoryBasic {
	//TODO I'm going to want something more like a decorator when leader expansion
	public GamePhaseFactoryBoard() {
		super();
		this.addPhase(5, () -> new ListBoards(), new GamePhaseStartNull());
	}
}
