package com.jtriemstra.wonders.api.model.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LocalResourceEvaluator {
	private List<HashMap<ResourceType, Integer>> results;
	private int maxTradeCost;
	private ResourceCost remainingResourceCost;
	
	public LocalResourceEvaluator(List<ResourceSet> board) {
		results = new ArrayList<>();
		
		recurse(board, new ArrayList<>());		
	}
	
	
	
	public boolean test(ResourceCost c) {
		for (HashMap<ResourceType, Integer> h : results) {
			if (c.isSatisfiedBy(h)) {
				return true;
			}
		}
		
		return false;
	}
	
	private void recurse(List<ResourceSet> input, List<ResourceType> result) {
		if (input.size() == 0) {
			HashMap<ResourceType, Integer> resultCounts = new HashMap<>();
			for (ResourceType r : result) {
				resultCounts.merge(r, 1, Integer::sum);
			}
			this.results.add(resultCounts);
			
			return;
		}
		
		ResourceSet r = input.remove(0);
		Iterator<ResourceType> i = r.iterator();
		while(i.hasNext()) {
			result.add(0, i.next());
			recurse(input, result);
			result.remove(0);
		}
		input.add(0, r);
	}
}
