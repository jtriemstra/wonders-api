package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.BaseAction;

public interface GamePhaseFactory {
	public ActionFactory getAction();
	public GamePhaseStart getStartFunction();
	public void addPhase(double order, ActionFactory action, GamePhaseStart startFunction);
	public void nextPhase();
	public boolean hasNext();
	boolean phaseComplete(Game g);
	public void phaseLoop(Game game);
	public void phaseEnd(Game game);
	public Phase getCurrentPhase();
}
