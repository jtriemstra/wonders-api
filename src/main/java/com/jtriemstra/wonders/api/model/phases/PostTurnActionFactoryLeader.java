package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.action.PlayCardsAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.state.MemoryStateService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostTurnActionFactoryLeader {
	
	private MemoryStateService stateService;
	
	public PostTurnActions getPostTurnActions() {
		PostTurnActions postTurnActions = new PostTurnActions(stateService);
		postTurnActions.add(null, new PlayCardsAction());
		
		return postTurnActions;
	}
}
