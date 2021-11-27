package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.action.PlayCardsAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;

public class PostTurnActionFactoryLeader {
	public PostTurnActions getPostTurnActions() {
		PostTurnActions postTurnActions = new PostTurnActions();
		postTurnActions.add(null, new PlayCardsAction());
		
		return postTurnActions;
	}
}
