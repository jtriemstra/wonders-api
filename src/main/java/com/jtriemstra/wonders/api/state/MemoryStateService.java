package com.jtriemstra.wonders.api.state;

import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;

import lombok.SneakyThrows;

@Service
public class MemoryStateService implements StateService {
	
	private Map<String, Map<String, BaseResponse>> responses;
	private List<GameState> gameStates;
	private Clock clock;	
	private ObjectMapper objectMapper;
	
	public MemoryStateService(Clock clock, ObjectMapper objectMapper) {
		responses = new HashMap<>();
		gameStates = new ArrayList<>();
		this.clock = clock;
		this.objectMapper = objectMapper;
	}
	
	public void createGame(String gameName) {
		Map<String, BaseResponse> thisGame = new HashMap<>();
		responses.put(gameName, thisGame);
	}
	
	public void addPlayer(String gameName, String playerName) {
		responses.get(gameName).put(playerName, null);
	}
	
	//TODO: how to clear things out on an end game, or for abandoned games? Abandoned is broader...how do I get rid of them in general?
	@SneakyThrows	
	public void changeGameState(String playerName, String actionName, Game game) {
		// TODO: by passing a Game reference, the state will be mutable and this isn't a log of state at time.
		gameStates.add(new GameState(clock.instant(), actionName, playerName, game));
	}
	
	public void recordLastResponse(String gameName, String playerName, BaseResponse response) {
		Map<String, BaseResponse> thisGame = responses.get(gameName);
		if (thisGame != null) {
			if (thisGame.containsKey(playerName)) {
				thisGame.put(playerName, response);
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
		Map<String, BaseResponse> thisGame = responses.get(gameName);
		if (thisGame != null) {
			if (thisGame.containsKey(playerName)) {
				return thisGame.get(playerName);
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
