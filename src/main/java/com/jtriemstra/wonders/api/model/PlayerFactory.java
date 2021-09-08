package com.jtriemstra.wonders.api.model;

import com.jtriemstra.wonders.api.notifications.NotificationService;

public interface PlayerFactory {
	
	public Player createPlayer(String playerName);
}
