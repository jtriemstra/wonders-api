package com.jtriemstra.wonders.api.model.playbuildrules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

public class TradingTests {

	Random r = new Random();
	
	@Test
	public void random_when_trading_no_duplicates() {
		PlayableBuildable mock = Mockito.mock(PlayableBuildable.class);
		Mockito.when(mock.getUnusedResources()).thenReturn(getUnusedResources(3));
		Mockito.when(mock.getResourceCost()).thenReturn(getResourceCost());
		Player left = Mockito.mock(Player.class);
		Player right = Mockito.mock(Player.class);
		Mockito.when(left.getResources(false)).thenReturn(getUnusedResources(5 + r.nextInt(6)));
		Mockito.when(right.getResources(false)).thenReturn(getUnusedResources(5 + r.nextInt(6)));
		Mockito.when(mock.getLeftNeighbor()).thenReturn(left);
		Mockito.when(mock.getRightNeighbor()).thenReturn(right);
		Mockito.when(mock.getCoinsAvailableForTrade()).thenReturn(12);
		
		Trading test = new Trading();
		test.setNextRule(new CantExecute());
		
		System.out.println("SELF: " + print(mock.getUnusedResources()));
		System.out.println("LEFT: " + print(mock.getLeftNeighbor().getResources(false)));
		System.out.println("RIGHT: " + print(mock.getRightNeighbor().getResources(false)));
		System.out.println("COST: " + print(mock.getResourceCost()));
		PlayableBuildableResult r = test.evaluate(mock);
		System.out.println(r.getCostOptions() == null ? r.getStatus() : r.getCostOptions().size());
		if (r.getCostOptions() != null) {
			for (TradeCost tc : r.getCostOptions()) {
				System.out.print(tc.left + "|" + tc.right + " ");
			}
		}
	}
	
	@Test
	public void returns_duplicate() {
		PlayableBuildable mock = Mockito.mock(PlayableBuildable.class);
		Mockito.when(mock.getUnusedResources()).thenReturn(Arrays.asList(new ResourceSet(ResourceType.GLASS), new ResourceSet(ResourceType.TEXTILE, ResourceType.GLASS), new ResourceSet(ResourceType.PAPER)));
		Mockito.when(mock.getResourceCost()).thenReturn(new ResourceCost(new ResourceType[] {ResourceType.TEXTILE, ResourceType.ORE, ResourceType.WOOD}));
		Player left = Mockito.mock(Player.class);
		Player right = Mockito.mock(Player.class);
		Mockito.when(left.getResources(false)).thenReturn(Arrays.asList(new ResourceSet(ResourceType.PAPER), new ResourceSet(ResourceType.PAPER), new ResourceSet(ResourceType.BRICK), new ResourceSet(ResourceType.TEXTILE,ResourceType.BRICK,ResourceType.STONE), new ResourceSet(ResourceType.BRICK), new ResourceSet(ResourceType.PAPER), new ResourceSet(ResourceType.PAPER), new ResourceSet(ResourceType.STONE), new ResourceSet(ResourceType.TEXTILE)));
		Mockito.when(right.getResources(false)).thenReturn(Arrays.asList(new ResourceSet(ResourceType.BRICK,ResourceType.GLASS), new ResourceSet(ResourceType.WOOD), new ResourceSet(ResourceType.BRICK,ResourceType.WOOD), new ResourceSet(ResourceType.TEXTILE,ResourceType.ORE,ResourceType.WOOD), new ResourceSet(ResourceType.GLASS), new ResourceSet(ResourceType.TEXTILE)));
		Mockito.when(mock.getLeftNeighbor()).thenReturn(left);
		Mockito.when(mock.getRightNeighbor()).thenReturn(right);
		Mockito.when(mock.getCoinsAvailableForTrade()).thenReturn(12);
		
		Trading test = new Trading();
		test.setNextRule(new CantExecute());
		
		System.out.println("SELF: " + print(mock.getUnusedResources()));
		System.out.println("LEFT: " + print(mock.getLeftNeighbor().getResources(false)));
		System.out.println("RIGHT: " + print(mock.getRightNeighbor().getResources(false)));
		System.out.println("COST: " + print(mock.getResourceCost()));
		PlayableBuildableResult r = test.evaluate(mock);
		System.out.println(r.getCostOptions() == null ? r.getStatus() : r.getCostOptions().size());
		if (r.getCostOptions() != null) {
			for (TradeCost tc : r.getCostOptions()) {
				System.out.print(tc.left + "|" + tc.right + " ");
			}
		}
	}
	
	private String print(List<ResourceSet> in) {
		return in.stream().map(Object::toString).collect(Collectors.joining(" "));
	}
	
	private String print(ResourceCost rc) {
		return rc.toString();
	}
	
	private List<ResourceSet> getUnusedResources(int numberOfResources) {
		List<ResourceSet> results = new ArrayList<>();

		for (int i=0; i<numberOfResources; i++) {
			int x = r.nextInt(8); 
			if (x < 6) {
				results.add(new ResourceSet(getRandomType()));	
			}
			else if (x < 7){
				results.add(new ResourceSet(getRandomType(), getRandomType()));
			}
			else {
				results.add(new ResourceSet(getRandomType(), getRandomType(), getRandomType()));
			}	
		}
		
		return results;
	}
	
	private ResourceCost getResourceCost() {
		List<ResourceType> types = new ArrayList<>();
		int numberOfResources = 2 + r.nextInt(3);
		for (int i=0; i<numberOfResources; i++) {
			types.add(getRandomType());
		}
		
		ResourceCost result = new ResourceCost(types.toArray(new ResourceType[types.size()]));
		return result;
	}
	
	private ResourceType getRandomType() {
		ResourceType[] types = new ResourceType[] {ResourceType.BRICK, ResourceType.GLASS, ResourceType.ORE, ResourceType.PAPER, ResourceType.STONE, ResourceType.TEXTILE, ResourceType.WOOD};
		return types[r.nextInt(types.length)];
	}
}
