package com.jtriemstra.wonders.api.model.board;

import java.util.Map;

import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardSourceLeadersDecorator implements BoardSource {

	private BoardSource inner;
	private LeaderDeck leaderDeck;
	
	@Override
	public Map<String, BoardFactoryMethod> getBoards() {
		Map<String, BoardFactoryMethod> boards = inner.getBoards();
		boards.put("Rome", b -> new Rome(b, leaderDeck));
		return boards;
	}	
}
