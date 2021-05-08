package com.jtriemstra.wonders.api.model.phases;

import java.util.ArrayList;
import java.util.List;

public class GamePhaseFactoryBasic implements GamePhaseFactory {

	@Override
	public List<Phase> getPhases() {
		List<Phase> result = new ArrayList<>();
		
		result.add(new Phase(6.0, 
				g -> {
					int coins = g.getInitialCoins().getCoins();
					g.doForEachPlayer(p -> {
						p.claimStartingBenefit(g);
						p.setCoins(coins);
					});
				}));
		result.add(new AgePhase(1));
		result.add(new AgePhase(2));
		result.add(new AgePhase(3));
		
		return result;
	}

}
