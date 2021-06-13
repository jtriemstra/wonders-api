package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.ListBoards;
import com.jtriemstra.wonders.api.model.board.BoardManager;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class GamePhaseStartBoard implements GamePhaseStart {
	
	private BoardManager boardManager;

	@Override
	public void start(Game g) {
		log.info("GamePhaseStartBoard");
		
		g.doForEachPlayer(p -> {
			log.info("adding ListBoards to " + p.getName());
			p.addNextAction(new ListBoards(boardManager));
		});		
	}

}
