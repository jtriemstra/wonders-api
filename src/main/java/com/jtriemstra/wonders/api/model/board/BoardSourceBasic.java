package com.jtriemstra.wonders.api.model.board;

import java.util.HashMap;
import java.util.Map;

public class BoardSourceBasic implements BoardSource {

	@Override
	public Map<String, BoardFactoryMethod> getBoards() {
		Map<String, BoardFactoryMethod> result = new HashMap<>();
		result.put("Rhodes", b -> new Rhodes(b));
		result.put("Giza", b -> new Giza(b));
		result.put("Olympia", b -> new Olympia(b));
		result.put("Ephesus", b -> new Ephesus(b));
		result.put("Babylon", b -> new Babylon(b));
		result.put("Halikarnassos", b -> new Halikarnassos(b));
		result.put("Alexandria", b -> new Alexandria(b));
		return result;
	}
}
