package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.StartAge;
import com.jtriemstra.wonders.api.model.action.WaitTurn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GamePhaseStartBasic implements GamePhaseStart {

	@Override
	public void start(Game g) {
		log.info("GamePhaseStartBasic");
		
		g.doForEachPlayer(p -> {
			log.info("adding WaitTurn to " + p.getName());
			
			// not sure this is necessary, but just sits on the queue as a fallback next action.
			p.addNextAction(new WaitTurn());
			p.addNextAction(new StartAge());
		});
	}

}
