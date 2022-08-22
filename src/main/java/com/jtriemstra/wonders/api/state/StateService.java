package com.jtriemstra.wonders.api.state;

import org.springframework.stereotype.Service;

import com.jtriemstra.wonders.api.model.Game;

@Service
public interface StateService {
	public void changeGameState(String actionName, Game game);
}
