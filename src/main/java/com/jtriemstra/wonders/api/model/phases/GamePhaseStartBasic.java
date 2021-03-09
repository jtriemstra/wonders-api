package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.WaitTurn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GamePhaseStartBasic implements GamePhaseStart {

	@Override
	public void start(Game g) {
		log.info("GamePhaseStartBasic");
		g.startAge();
		
		//TODO: not sure I want to expose the players out of the Game...haven't really needed it so far
		for (Player p : g.getPlayers()) {
			log.info("adding WaitTurn to " + p.getName());
			
			// not sure this is necessary, but just sits on the queue as a fallback next action.
			p.addNextAction(new WaitTurn());
			p.startTurn();
		}
	}

}
