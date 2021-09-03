package com.jtriemstra.wonders.api.model.resource;

import java.util.HashMap;
import java.util.List;

public class ResourceCost {

	HashMap<ResourceType, Integer> costs;
	
	//TODO: (low) convert cards to using this instead of Resource[]
	public ResourceCost(ResourceType[] in) {
		costs = new HashMap<>();
		for (ResourceType r : in) {
			costs.merge(r, 1, Integer::sum);
		}
	}
	
	public boolean isNeeded(ResourceSet r) {
		for (ResourceType t : costs.keySet()) {
			if (costs.get(t) > 0 && r.matches(t)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isNeeded(ResourceType r) {
		return costs.containsKey(r);
	}

	public void reduce(ResourceType currentAvailableType) {
		costs.merge(currentAvailableType, -1, Integer::sum);
	}
	
	public boolean isComplete() {
		return costs.values().stream().mapToInt(i -> i).max().getAsInt() == 0;
	}
	
	public boolean isSatisfiedBy(HashMap<ResourceType, Integer> available) {
		for (ResourceType r : costs.keySet()) {
			if (costs.get(r) > 0 && (!available.containsKey(r) || available.get(r) < costs.get(r))) {
				return false;
			}
		}
		
		return true;
	}

	public boolean isSatisfiedBy(List<ResourceType> in) {
		HashMap<ResourceType, Integer> available = new HashMap<>();
		for (ResourceType r : in) {
			available.merge(r, 1, Integer::sum);
		}
		
		return isSatisfiedBy(available);
	}
	
	public String toString() {
		String result = "";
		for (ResourceType rt : costs.keySet()) {
			result += rt + "(" + costs.get(rt) + ") ";
		}
		return result;
	}
}
