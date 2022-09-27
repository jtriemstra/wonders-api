package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.action.PlayCardsAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.state.StateService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostTurnActionFactoryLeader {
	
	private StateService stateService;
	
	public PostTurnActions getPostTurnActions() {
		PostTurnActions postTurnActions = new PostTurnActions(stateService);
		postTurnActions.add(null, new PlayCardsAction());
		
		return postTurnActions;
	}
}
