package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.dto.request.BaseRequest;
import com.jtriemstra.wonders.api.dto.request.ChooseScienceRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Science;
import com.jtriemstra.wonders.api.model.card.ScienceType;

public class ChooseScience implements BaseAction, OptionAction {

	@Override
	public String getName() {
		//TODO: (low) is there a more generic option here? choose science and choose guild card are the two I know about right now
		return "chooseScience";
	}

	@Override
	public ActionResponse execute(BaseRequest request, Player player, Game game) {
		ChooseScienceRequest chooseRequest = (ChooseScienceRequest) request;
		
		player.popAction();
		
		player.addScienceProvider(() -> {return new Science(chooseRequest.getChoice());});
		
		return new ActionResponse();
	}

	@Override
	public Object[] getOptions() {
		return new ScienceType[] {ScienceType.TABLET, ScienceType.GEAR, ScienceType.COMPASS};
	}
}
