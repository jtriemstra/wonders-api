package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.action.ListBoards;
import com.jtriemstra.wonders.api.model.board.BoardManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardPhase extends Phase {
	
	private AtomicBoolean isPhaseStarted = new AtomicBoolean();
	private BoardManager boardManager;
	
	public BoardPhase(BoardManager boardManager) {
		super(5.0);
		isPhaseStarted.set(false);
		this.boardManager = boardManager;
	}
	
	@Override
	public void startPhase(Game g) {
		log.info("startPhase");
		isPhaseStarted.set(true);
		
		g.doForEachPlayer(p -> {
			log.info("adding ListBoards to " + p.getName());
			p.addNextAction(new ListBoards(boardManager));
		});	
	}

}
