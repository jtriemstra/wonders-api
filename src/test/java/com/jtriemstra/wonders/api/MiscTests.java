package com.jtriemstra.wonders.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MiscTests {

	@Test
	public void streamCountOfLists() {
		Map<Integer, List<Integer>> defeats = new HashMap<>();
		defeats.put(1, new ArrayList<>());
		defeats.put(2, new ArrayList<>());
		defeats.put(3, new ArrayList<>());
		
		defeats.get(1).add(6);
		defeats.get(1).add(7);
		defeats.get(2).add(8);
		defeats.get(2).add(9);
		defeats.get(2).add(10);
		
		int x = defeats.values().stream().mapToInt(l -> l.size()).reduce(0, Integer::sum);
		
		Assertions.assertEquals(5, x);
	}
}
