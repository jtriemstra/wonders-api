package com.jtriemstra.wonders.api.model.board;

import java.util.Map;

public interface BoardSource {
	public Map<String, BoardFactoryMethod> getBoards();
}
