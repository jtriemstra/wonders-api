package com.jtriemstra.wonders.api.model.action;

import java.util.List;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.AdditionalLeaderResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.phases.AgePhase;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowAdditionalLeaders implements PostTurnAction, BaseAction {
	
	private List<Card> newLeaders;

	@Override
	public double getOrder() {
		return 0.7;
	}

	@Override
	public String getName() {
		//TOOD: could other actions that just show state have the same name? getEndOfAge for one.
		return "showLeaders";
	}

	@Override
	public ActionResponse execute(BaseRequest request, IPlayer player, Game game) {
		player.popAction();
		
		AdditionalLeaderResponse r = new AdditionalLeaderResponse();
		r.setNewLeaders(newLeaders);
		
		return r;
	}
	
	@Override
	public boolean isSingleUse() {
		return true;
	}

}
