package com.jtriemstra.wonders.api.model.action;

import com.jtriemstra.wonders.api.model.card.Card;

public interface CardRemoveStrategy {
	public Card removeFromSource(String name);
}