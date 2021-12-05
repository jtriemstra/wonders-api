package com.jtriemstra.wonders.api.model;

import com.jtriemstra.wonders.api.notifications.NotificationService;

public interface PlayerFactory {
	
	public IPlayer createPlayer(String playerName);
}
