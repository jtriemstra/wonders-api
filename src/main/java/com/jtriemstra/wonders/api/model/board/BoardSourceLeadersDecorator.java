package com.jtriemstra.wonders.api.model.board;

import java.util.Map;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardSourceLeadersDecorator implements BoardSource {

	private BoardSource inner;
	
	@Override
	public Map<String, BoardFactoryMethod> getBoards() {
		Map<String, BoardFactoryMethod> boards = inner.getBoards();
		boards.put("Rome", b -> new Rome(b));
		return boards;
	}
}
