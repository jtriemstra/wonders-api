package com.jtriemstra.wonders.api.state;

import java.util.List;

import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;

public class DistributedStateService implements StateService {

	@Override
	public void startGame(String gameName, List<String> playerNames) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeGameState(String actionName, Game game) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recordLastResponse(String gameName, String playerName, BaseResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BaseResponse getLastResponse(String gameName, String playerName) {
		// TODO Auto-generated method stub
		return null;
	}

}
