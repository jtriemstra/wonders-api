package com.jtriemstra.wonders.api.model.phases;

import com.jtriemstra.wonders.api.model.DiscardPile;
import com.jtriemstra.wonders.api.model.action.DiscardFinalCardAction;
import com.jtriemstra.wonders.api.model.action.PlayCardsAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.ResolveConflictAction;
import com.jtriemstra.wonders.api.state.StateService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostTurnActionFactoryDefault {
	
	DiscardPile discard;
	StateService stateService;
	
	public PostTurnActions getPostTurnActions() {
		PostTurnActions postTurnActions1 = new PostTurnActions(stateService);
		postTurnActions1.add(null, new PlayCardsAction());
		postTurnActions1.add(null, new DiscardFinalCardAction(discard));
		postTurnActions1.add(null, new ResolveConflictAction());
		
		return postTurnActions1;
	}
	
}
