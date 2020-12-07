package com.jtriemstra.wonders.api.model.resource;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ResourceSet {
	private Set<ResourceType> possibleResources;
	private ResourceType singleResource;
	
	public ResourceSet(ResourceType... resourceTypes) {
		this.possibleResources = new HashSet<>();
		for (ResourceType r : resourceTypes) {
			possibleResources.add(r);
		}
		
		if (resourceTypes.length == 1) {
			singleResource = resourceTypes[0];
		}
	}
	
	public boolean isSingle() {
		return possibleResources.size() == 1;
	}
	
	public ResourceType getSingle() {
		return singleResource;
	}
	
	public boolean matches(ResourceType input) {
		for (ResourceType r : possibleResources) {
			if (r == input) return true;
		}
		
		return false;
	}
	
	public Iterator<ResourceType> iterator(){
		return possibleResources.iterator();
	}
}
