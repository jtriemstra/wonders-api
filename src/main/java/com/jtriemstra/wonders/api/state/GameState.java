package com.jtriemstra.wonders.api.state;

import java.time.Instant;

import com.jtriemstra.wonders.api.model.Game;

import lombok.Data;

@Data
public class GameState {
	private Instant timestamp;
	private String triggeringAction;
	private String triggeringPlayer;
	private Game currentState;
	
	public GameState(Instant timestamp, String triggeringAction, String triggeringPlayer, Game currentState) {
		this.timestamp = timestamp;
		this.triggeringAction = triggeringAction;
		this.triggeringPlayer = triggeringPlayer;
		this.currentState = currentState;
	}
}
