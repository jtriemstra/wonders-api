package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

public class GamePhaseFactoryBasic implements GamePhaseFactory {

	@Override
	public List<Phase> getPhases() {
		List<Phase> result = new ArrayList<>();
		
		result.add(new AgePhase(1));
		result.add(new AgePhase(2));
		result.add(new AgePhase(3));
		
		return result;
	}

}
