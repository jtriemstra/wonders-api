package com.jtriemstra.wonders.api.model.card.leaders;

import com.jtriemstra.wonders.api.model.card.Card;

public class Aristotle extends Card {

	public Aristotle() {
		super(3,1);
	}

	@Override
	public String getType() {
		return "Leader";
	}

}
