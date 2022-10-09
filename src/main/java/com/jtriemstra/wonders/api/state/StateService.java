package com.jtriemstra.wonders.api.state;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;

@Service
public interface StateService {
	// TODO: this may not be relevant for DB-backing...keep it in the interface?
	void createGame(String gameName);
	
	void addPlayer(String gameName, String playerName);
	void changeGameState(String playerName, String actionName, Game game);
	void recordLastResponse(String gameName, String playerName, BaseResponse response);
	BaseResponse getLastResponse(String gameName, String playerName);
}
