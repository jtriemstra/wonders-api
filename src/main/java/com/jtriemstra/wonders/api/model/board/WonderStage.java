package com.jtriemstra.wonders.api.model.board;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

public abstract class WonderStage {
	
	public int getCoinCost() {
		return 0;
	}
	public ResourceType[] getResourceCost() {
		return null;
	}
	
	public abstract void build(IPlayer player, Game game);
}
