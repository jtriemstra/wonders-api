package com.jtriemstra.wonders.api.state;

import org.springframework.stereotype.Service;

import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;

@Service
public class MemoryStateService implements StateService {
		
	public void changeGameState(String actionName, Game game) {
		
	}
	
	public void recordLastResponse(String gameName, String playerName, BaseResponse response) {
		
	}
	
}
