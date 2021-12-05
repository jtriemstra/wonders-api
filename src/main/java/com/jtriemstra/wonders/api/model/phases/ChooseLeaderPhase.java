package com.jtriemstra.wonders.api.model.phases;

import java.util.concurrent.atomic.AtomicBoolean;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;
import com.jtriemstra.wonders.api.model.action.GetOptionsRecruitLeader;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.WaitTurn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChooseLeaderPhase extends Phase {
	private AtomicBoolean isPhaseStarted = new AtomicBoolean();
	private PostTurnActions postTurnActions;
	private int age;
	
	public ChooseLeaderPhase(int age, PostTurnActions postTurnActions) {
		super(9.5 + age);
		this.age = age;
		isPhaseStarted.set(false);
		this.postTurnActions = postTurnActions;
	}
	
	public int getAge() {
		return age;
	}
		
	@Override
	public void endPhase(Game g) {
		log.info("endPhase");
		
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
				
		g.doForEachPlayer(p -> {
			log.info("adding WaitTurn to " + p.getName());
			
			// not sure this is necessary, but just sits on the queue as a fallback next action.
			p.addNextAction(new WaitTurn());
			p.addNextAction(new GetOptionsRecruitLeader());
		});
	
		isPhaseStarted.set(true);
	}
	
	@Override
	public boolean phaseStarted() {
		return isPhaseStarted.get();
	}

	@Override
	public void handlePostTurnActions(Game g) {
		if (!phaseStarted()) {
			return;
		}
		//TODO: (low) the post game actions could be done simultaneously, rather than sequentially
		if (postTurnActions.hasNext()) {
			postTurnActions.doNext(this, g);
		}		
	}
	
	@Override
	public void addPostTurnAction(IPlayer p, PostTurnAction action) {
		postTurnActions.add(p, action);
	}

	@Override
	public void injectPostTurnAction(IPlayer p, PostTurnAction action, int additionalIndex) {
		postTurnActions.inject(p, action, additionalIndex);
	}
}
