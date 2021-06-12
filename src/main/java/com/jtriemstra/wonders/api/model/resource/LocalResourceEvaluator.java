package com.jtriemstra.wonders.api.model.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class LocalResourceEvaluator {
	private List<HashMap<ResourceType, Integer>> results;
	
	public LocalResourceEvaluator(List<ResourceSet> availableResources) {
		results = new ArrayList<>();
		
		buildAllCombinations(availableResources, new ArrayList<>());		
	}
	
	public boolean test(ResourceCost c) {
		for (HashMap<ResourceType, Integer> h : results) {
			if (c.isSatisfiedBy(h)) {
				return true;
			}
		}
		
		return false;
	}
	
	private void buildAllCombinations(List<ResourceSet> input, List<ResourceType> result) {
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
			buildAllCombinations(input, result);
			result.remove(0);
		}
		input.add(0, r);
	}
}
