package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.action.DiscardFinalCardAction;
import com.jtriemstra.wonders.api.model.action.PlayCardsAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.ResolveCommerceAction;
import com.jtriemstra.wonders.api.model.action.ResolveConflictAction;

public class PostTurnActionFactoryLeader {
	public PostTurnActions getPostTurnActions() {
		PostTurnActions postTurnActions = new PostTurnActions();
		postTurnActions.add(null, new PlayCardsAction());
		postTurnActions.add(null, new ResolveCommerceAction());
		
		return postTurnActions;
	}
}
