package com.jtriemstra.wonders.api.model.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.model.card.provider.NaturalTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TechTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;
import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

public class ResourceEvaluator2Tests {
	
	private List<EvalInfo> assembleResources(List<ResourceSet> board, List<ResourceSet> left, List<ResourceSet> right) {
		return assembleResources(board, left, right, 2, 2);
	}
	
	private List<EvalInfo> assembleResources(List<ResourceSet> board, List<ResourceSet> left, List<ResourceSet> right, int leftCost, int rightCost) {
		List<EvalInfo> allResources = new ArrayList<>();
		allResources.add( new EvalInfo( board.stream().map( rs -> {return new ResourceWithTradeCost(rs, 0, "Self");} ).collect(Collectors.toList()), "Self"));
		allResources.add( new EvalInfo( left.stream().map( rs -> {return new ResourceWithTradeCost(rs, leftCost, "Left");} ).collect(Collectors.toList()), "Left"));
		allResources.add( new EvalInfo( right.stream().map( rs -> {return new ResourceWithTradeCost(rs, rightCost, "Right");} ).collect(Collectors.toList()), "Right"));
		return allResources;
	}
	
	@Test
	public void when_board_has_matching_combo_then_cost_zero() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		board.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = null;
		
		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(1, results.size());
		assertEquals(0, results.get(0).get("Left"));
		assertEquals(0, results.get(0).get("Right"));
	}
	
	@Test
	public void when_board_and_neighbor_has_match_then_cost_zero() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		board.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = null;
		
		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(1, results.size());
		assertEquals(0, results.get(0).get("Left"));
		assertEquals(0, results.get(0).get("Right"));
	}
	
	@Test
	public void when_each_neighbor_has_match_then_cost_two() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = null;
		
		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(2, results.size());
		assertEquals(2, results.get(0).get("Left") + results.get(0).get("Right"));
		assertEquals(2, results.get(1).get("Left") + results.get(1).get("Right"));		
		
	}
	
	@Test
	public void when_each_neighbor_has_multiple_match_then_cost_two() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		right.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = null;
		
		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(2, results.size());
		assertEquals(2, results.get(0).get("Left") + results.get(0).get("Right"));
		assertEquals(2, results.get(1).get("Left") + results.get(1).get("Right"));		
				
	}

	@Test
	public void when_have_trading_provider_then_cost_cheaper() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new NaturalTradingProvider(CardDirection.BOTH));
		
		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(2, results.size());
		assertEquals(2, results.get(0).get("Left") + results.get(0).get("Right"));
		assertEquals(2, results.get(1).get("Left") + results.get(1).get("Right"));		
		
	}
	

	@Test
	public void when_have_trading_provider_for_one_neighbor_then_cost_cheaper() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new NaturalTradingProvider(CardDirection.LEFT));
		
		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right, 1, 2), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(2, results.size());
		assertEquals(1, Math.min(results.get(0).get("Left") + results.get(0).get("Right"), results.get(1).get("Left") + results.get(1).get("Right")));
		assertEquals(2, Math.max(results.get(0).get("Left") + results.get(0).get("Right"), results.get(1).get("Left") + results.get(1).get("Right")));	
		
	}

	@Test
	public void when_have_trading_provider_for_wrong_neighbor_then_cost_not_cheaper() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new NaturalTradingProvider(CardDirection.LEFT));

		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right, 1, 2), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(1, results.size());
		assertEquals(2, results.get(0).get("Left") + results.get(0).get("Right"));	
				
	}
	
	@Test
	public void when_have_tech_trading_provider_then_cost_cheaper() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.GLASS}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.GLASS}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.GLASS}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new TechTradingProvider(CardDirection.BOTH));

		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right, 1, 1), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(2, results.size());
		assertEquals(1, results.get(0).get("Left") + results.get(0).get("Right"));
		assertEquals(1, results.get(1).get("Left") + results.get(1).get("Right"));	
				
	}
	
	@Test
	public void when_need_multiple_resources_then_cost_more_than_2() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.GLASS}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.PAPER}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.GLASS, ResourceType.PAPER}); 
		TradingProviderList trades = new TradingProviderList();

		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(1, results.size());
		assertEquals(4, results.get(0).get("Left") + results.get(0).get("Right"));
				
	}
	
	@Test
	public void when_need_multiple_resources_then_get_discount_for_both() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.GLASS}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.PAPER}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.GLASS, ResourceType.PAPER}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new TechTradingProvider(CardDirection.BOTH));

		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right, 1, 1), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(1, results.size());
		assertEquals(2, results.get(0).get("Left") + results.get(0).get("Right"));
	}

	@Test
	public void when_need_multiple_resources_then_get_discount_for_one() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.GLASS}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.WOOD}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.GLASS, ResourceType.WOOD}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new TechTradingProvider(CardDirection.BOTH));
		

		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right, 2, 1), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(1, results.size());
		assertEquals(3, results.get(0).get("Left") + results.get(0).get("Right"));
	}	
	
	@Test
	public void investigate_why_temple_unplayable() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		board.add(new ResourceSet(new ResourceType[] {ResourceType.PAPER}));
		board.add(new ResourceSet(new ResourceType[] {ResourceType.STONE}));
		board.add(new ResourceSet(new ResourceType[] {ResourceType.BRICK}));
		board.add(new ResourceSet(new ResourceType[] {ResourceType.STONE, ResourceType.WOOD}));
		board.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.BRICK}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.GLASS}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.PAPER}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.TEXTILE}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.WOOD}));
		right.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 4; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.WOOD, ResourceType.BRICK, ResourceType.GLASS}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new TechTradingProvider(CardDirection.BOTH));
		

		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right, 1, 1), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(1, results.size());
		assertEquals(1, results.get(0).get("Left"));
	}
	
	@Test
	public void investigate_barracks_cost() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		left.add(new ResourceSet(new ResourceType[] {ResourceType.ORE}));
		//left.add(new ResourceSet(new ResourceType[] {ResourceType.TEXTILE}));
		//left.add(new ResourceSet(new ResourceType[] {ResourceType.WOOD}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.BRICK, ResourceType.ORE}));
		
		///board.add(new ResourceSet(new ResourceType[] {ResourceType.STONE}));
		//board.add(new ResourceSet(new ResourceType[] {ResourceType.STONE}));
		//board.add(new ResourceSet(new ResourceType[] {ResourceType.STONE, ResourceType.WOOD}));
		
		//right.add(new ResourceSet(new ResourceType[] {ResourceType.PAPER}));
		right.add(new ResourceSet(new ResourceType[] {ResourceType.ORE}));
		//right.add(new ResourceSet(new ResourceType[] {ResourceType.GLASS}));
		
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new NaturalTradingProvider(CardDirection.RIGHT));
		

		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right, 2, 1), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(2, results.size());
		assertEquals(1, results.get(0).get("Right") + results.get(1).get("Right"));
		assertEquals(2, results.get(0).get("Left") + results.get(1).get("Left"));
	}
	

	@Test
	public void investigate_performance_issues() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		board.add(new ResourceSet(new ResourceType[] {ResourceType.WOOD, ResourceType.STONE}));
		board.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD, ResourceType.STONE, ResourceType.BRICK}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.ORE}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.ORE}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.ORE}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.WOOD}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.BRICK}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE, ResourceType.BRICK}));
		right.add(new ResourceSet(new ResourceType[] { ResourceType.WOOD}));
		right.add(new ResourceSet(new ResourceType[] { ResourceType.WOOD}));
		
		int maxTradeCost = 7; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.WOOD, ResourceType.ORE, ResourceType.ORE, ResourceType.BRICK}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new TechTradingProvider(CardDirection.BOTH));
		trades.add(new NaturalTradingProvider(CardDirection.LEFT));
		trades.add(new NaturalTradingProvider(CardDirection.RIGHT));		
		
		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right, 1, 1), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(1, results.size());
		assertEquals(2, results.get(0).get("Left") + results.get(0).get("Right"));
	}

	@Test
	public void should_return_three_options() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		left.add(new ResourceSet(new ResourceType[] {ResourceType.ORE}));
		left.add(new ResourceSet(new ResourceType[] {ResourceType.ORE}));
		right.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		right.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 7; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE, ResourceType.ORE}); 
		TradingProviderList trades = new TradingProviderList();
		
		TradingResourceEvaluator2 eval = new TradingResourceEvaluator2(assembleResources(board, left, right), maxTradeCost, remainingResourceCost);
		List<TradeCost> results = eval.findMinCost(); 
		assertEquals(3, results.size());
		assertEquals(4, results.get(0).get("Left") + results.get(0).get("Right"));
		assertEquals(4, results.get(1).get("Left") + results.get(1).get("Right"));
		assertEquals(4, results.get(2).get("Left") + results.get(2).get("Right"));
	}

}
