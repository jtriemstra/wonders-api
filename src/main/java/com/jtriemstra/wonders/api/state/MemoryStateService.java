package com.jtriemstra.wonders.api.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;

@Service
public class MemoryStateService implements StateService {
	
	private Map<String, Map<String, List<BaseResponse>>> responses;
	
	public MemoryStateService() {
		responses = new HashMap<>();
	}
	
	public void startGame(String gameName, List<String> playerNames) {
		Map<String, List<BaseResponse>> thisGame = new HashMap<>();
		playerNames.forEach(n -> thisGame.put(n, new ArrayList<>()));
		responses.put(gameName, thisGame);
	}
	
	//TODO: how to clear things out on an end game, or for abandoned games?
		
	public void changeGameState(String actionName, Game game) {
		
	}
	
	public void recordLastResponse(String gameName, String playerName, BaseResponse response) {
		Map<String, List<BaseResponse>> thisGame = responses.get(gameName);
		if (thisGame != null) {
			List<BaseResponse> playerResponses = thisGame.get(playerName);
			if (playerResponses != null) {
				playerResponses.add(response);
			}
			else {
				throw new RuntimeException("Tried to log a response for a player that doesn't exist");
			}
		}
		else {
			throw new RuntimeException("Tried to log a response for a game that doesn't exist");
		}
		
	}
	
	public BaseResponse getLastResponse(String gameName, String playerName) {
		Map<String, List<BaseResponse>> thisGame = responses.get(gameName);
		if (thisGame != null) {
			List<BaseResponse> playerResponses = thisGame.get(playerName);
			if (playerResponses != null) {
				if (playerResponses.size() > 0) {
					return playerResponses.get(playerResponses.size() - 1);
				}
				else {
					return new BaseResponse();
				}
			}
			else {
				throw new RuntimeException("Tried to get a response for a player that doesn't exist");
			}
		}
		else {
			throw new RuntimeException("Tried to get a response for a game that doesn't exist");
		}
	}
	
}
