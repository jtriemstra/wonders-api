package com.jtriemstra.wonders.api.model.phases;

public interface PhaseMatcher {
	public boolean matches(Phase p, GameFlow f);
}
