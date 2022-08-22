package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.phases.Phase;
import com.jtriemstra.wonders.api.state.MemoryStateService;
import com.jtriemstra.wonders.api.state.StateService;


// TODO: I think these annotations aren't actually getting used. Either make them useful or remove them
@Component
@Scope("prototype")
public class PostTurnActions {
	private List<PostTurnDefinition> actions = new ArrayList<>();
	private int currentIteratorIndex = -1;
	
	// TODO: constructor inject, depending on if this is actually used as a bean
	private StateService stateService;
	
	public PostTurnActions(MemoryStateService stateService) {
		this.stateService = stateService;
	}
				
	public int size() {
		return actions.size();
	}

	public boolean hasNext() {
		
		boolean result = (currentIteratorIndex < actions.size() - 1);
		
		return result;
	}

	public void doNext(Phase currentPhase, Game game) {
		currentIteratorIndex++;
		PostTurnDefinition action = actions.get(currentIteratorIndex);

		if (action.action instanceof NonPlayerAction){
			((NonPlayerAction) action.action).execute(game);
			stateService.changeGameState(((NonPlayerAction) action.action).getName(), game);
			currentPhase.handlePostTurnActions(game);
		}
		else if (action.action instanceof BaseAction) {
			action.player.addNextAction((BaseAction) action.action);
		}
		
		if (action.action.isSingleUse()) {
			this.markForRemoval(action.player, action.action.getClass());
		}
	}

	public void add(IPlayer p, PostTurnAction action) {
		int index=0;
		while (index < actions.size() && actions.get(index).action.getOrder() < action.getOrder()) {
			index++;
		}
		
		actions.add(index, new PostTurnDefinition(p, action));
	}
	
	public void inject(IPlayer p, PostTurnAction action, int additionalIndex) {
		//TODO: maybe makes sense to check if the "native" order is still pending
		if (additionalIndex < 1) {
			throw new RuntimeException("this action will never be executed");
		}
		actions.add(currentIteratorIndex + additionalIndex, new PostTurnDefinition(p, action));
	}

	private void markForRemoval(IPlayer player, Class clazz) {
		for (PostTurnDefinition ptd : actions) {
			if (ptd.player == player && clazz.isInstance(ptd.action)) {
				ptd.remove = true;
				return;
			}
		}		
		
		throw new RuntimeException("an attempt was made to remove a PostTurnAction, but none matched");
	}
	
	private class PostTurnDefinition implements Comparable {
		public IPlayer player;
		public PostTurnAction action;
		public boolean remove = false;
		
		public PostTurnDefinition(IPlayer p, PostTurnAction a) {
			this.player = p;
			this.action = a;
		}

		@Override
		public int compareTo(Object arg0) {
			PostTurnAction action2 = ((PostTurnDefinition) arg0).action;
			return action.getOrder() < action2.getOrder() ? -1 : (action.getOrder() > action2.getOrder() ? 1 : 0);
		}
	}

	public void cleanUp() {
		currentIteratorIndex = -1;
		actions.removeIf(a -> (a.remove));
	}
}
