package com.jtriemstra.wonders.api.model;

import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.phases.Phases;

public interface GameFactory {
	public Game createGame(String name, int numberOfPlayers, Phases phases, BoardManager boardManager, DiscardPile discard);
}
