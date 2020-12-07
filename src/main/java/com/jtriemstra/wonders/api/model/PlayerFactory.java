package com.jtriemstra.wonders.api.model;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

public interface PlayerFactory {
	
	public Player createPlayer(String playerName);
}
