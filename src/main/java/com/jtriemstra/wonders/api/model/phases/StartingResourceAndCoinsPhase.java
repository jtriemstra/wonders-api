package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.ListBoards;
import com.jtriemstra.wonders.api.model.board.BoardManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartingResourceAndCoinsPhase extends Phase {
			
	public StartingResourceAndCoinsPhase() {
		super(6.0);	
	}
	
	@Override
	public void startPhase(Game g) {
		log.info("startPhase");
		
		int coins = g.getInitialCoins();
		g.doForEachPlayer(p -> {
			p.claimStartingBenefit(g);
			p.setCoins(coins);
		});
	}

}
