package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.ListBoards;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GamePhaseStartBoard implements GamePhaseStart {

	@Override
	public void start(Game g) {
		log.info("GamePhaseStartBoard");
		
		for (Player p : g.getPlayers()) {
			log.info("adding ListBoards to " + p.getName());
			
			p.addNextAction(new ListBoards());
		}
	}

}
