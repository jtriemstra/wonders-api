package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

@Component
@Scope("prototype")
public class PostTurnActions {
	private List<PostTurnDefinition> actions = new ArrayList<>();
	private int currentIteratorIndex = -1;
	private Game game;
	
	public PostTurnActions() {
			
	}
	
	//TODO: (low) is there a way to get this back in the constructor? Or, since this only gets called from the Game, maybe can be a parameter to that pmethod
	public void setGame(Game g) {
		game = g;
	}
		
	public int size() {
		return actions.size();
	}
	
	public void reset() {
		currentIteratorIndex = -1;
	}

	public boolean hasNext() {
		
		boolean result = (currentIteratorIndex < actions.size() - 1);
		
		return result;
	}

	public void doNext() {
		currentIteratorIndex++;
		PostTurnDefinition action = actions.get(currentIteratorIndex);
		//TODO: (low) not sure about this split between BaseAction and NonPlayerAction - no way to enforce one or the other that I know of
		if (action.action instanceof BaseAction) {
			action.player.addNextAction((BaseAction) action.action);
		}
		else if (action.action instanceof NonPlayerAction){
			((NonPlayerAction) action.action).execute(this.game);

			this.game.handlePostTurnActions();
		}
	}

	public void add(Player p, PostTurnAction action) {
		int index=0;
		while (index < actions.size() && actions.get(index).action.getOrder() < action.getOrder()) {
			index++;
		}
		
		actions.add(index, new PostTurnDefinition(p, action));
	}
	
	public void inject(Player p, PostTurnAction action, int additionalIndex) {
		//TODO: maybe makes sense to check if the "native" order is still pending
		if (additionalIndex < 1) {
			throw new RuntimeException("this action will never be executed");
		}
		actions.add(currentIteratorIndex + additionalIndex, new PostTurnDefinition(p, action));
	}

	public void markForRemoval(Player player, Class clazz) {
		for (PostTurnDefinition ptd : actions) {
			if (ptd.player == player && clazz.isInstance(ptd.action)) {
				ptd.remove = true;
				return;
			}
		}		
		
		throw new RuntimeException("an attempt was made to remove a PostTurnAction, but none matched");
	}
	
	private class PostTurnDefinition implements Comparable {
		public Player player;
		public PostTurnAction action;
		public boolean remove = false;
		
		public PostTurnDefinition(Player p, PostTurnAction a) {
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
