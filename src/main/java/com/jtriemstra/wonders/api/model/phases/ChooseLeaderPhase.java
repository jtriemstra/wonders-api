package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;
import com.jtriemstra.wonders.api.model.action.GetOptionsRecruitLeader;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.WaitTurn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChooseLeaderPhase extends Phase {
	private AtomicBoolean isPhaseStarted = new AtomicBoolean();
	private PostTurnActions postTurnActions;
	
	//TODO: add the postTurnActions
	public ChooseLeaderPhase(int age) {
		super(9.5 + age);
		isPhaseStarted.set(false);
	}
	
	@Override
	public boolean phaseComplete(Game g) {
		return true;
	}
	
	@Override
	public void endPhase(Game g) {
		log.info("endPhase");
		g.doForEachPlayer(p -> p.clearHand());
		
		postTurnActions.cleanUp();
		
		isPhaseStarted.set(false);
		
	}

	@Override
	public void loopPhase(Game g) {
		log.info("loopPhase");	
	}

	@Override
	public void startPhase(Game g) {
		log.info("startPhase");
		isPhaseStarted.set(true);
		
		g.doForEachPlayer(p -> {
			log.info("adding WaitTurn to " + p.getName());
			
			// not sure this is necessary, but just sits on the queue as a fallback next action.
			p.addNextAction(new WaitTurn());
			p.addNextAction(new GetOptionsRecruitLeader());
		});
		
	}
	
	@Override
	public boolean phaseStarted() {
		return isPhaseStarted.get();
	}
}
