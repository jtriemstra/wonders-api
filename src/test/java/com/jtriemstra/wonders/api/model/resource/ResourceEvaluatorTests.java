package com.jtriemstra.wonders.api.model.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.model.card.provider.NaturalTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TechTradingProvider;
import com.jtriemstra.wonders.api.model.card.provider.TradingProviderList;
import com.jtriemstra.wonders.api.model.card.provider.TradingProvider.CardDirection;
import com.jtriemstra.wonders.api.model.resource.ResourceCost;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator;

public class ResourceEvaluatorTests {
	
	
	public void when_board_has_matching_combo_then_cost_zero() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		board.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = null;
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(0, eval.findMinCost());
	}
	
	public void when_board_and_neighbor_has_match_then_cost_zero() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		board.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = null;
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(0, eval.findMinCost());
	}
	
	public void when_each_neighbor_has_match_then_cost_two() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.ORE}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = null;
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(2, eval.findMinCost());
		assertEquals(2, Math.max(eval.getLeftCost(), eval.getRightCost()));
		assertEquals(0, Math.min(eval.getLeftCost(), eval.getRightCost()));
	}
	
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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(2, eval.findMinCost());
		assertEquals(2, Math.max(eval.getLeftCost(), eval.getRightCost()));
		assertEquals(0, Math.min(eval.getLeftCost(), eval.getRightCost()));		
	}

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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(1, eval.findMinCost());
		assertEquals(1, Math.max(eval.getLeftCost(), eval.getRightCost()));
		assertEquals(0, Math.min(eval.getLeftCost(), eval.getRightCost()));
	}
	

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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(1, eval.findMinCost());
		assertEquals(1, Math.max(eval.getLeftCost(), eval.getRightCost()));
		assertEquals(0, Math.min(eval.getLeftCost(), eval.getRightCost()));
	}

	public void when_have_trading_provider_for_wrong_neighbor_then_cost_not_cheaper() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.ORE, ResourceType.WOOD}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.ORE}); 
		TradingProviderList trades = new TradingProviderList();
		trades.add(new NaturalTradingProvider(CardDirection.LEFT));
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(2, eval.findMinCost());
		assertEquals(2, Math.max(eval.getLeftCost(), eval.getRightCost()));
		assertEquals(0, Math.min(eval.getLeftCost(), eval.getRightCost()));
	}
	
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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(1, eval.findMinCost());
		assertEquals(1, Math.max(eval.getLeftCost(), eval.getRightCost()));
		assertEquals(0, Math.min(eval.getLeftCost(), eval.getRightCost()));
	}
	
	public void when_need_multiple_resources_then_cost_more_than_2() {
		List<ResourceSet> board = new ArrayList<>();
		List<ResourceSet> left = new ArrayList<>(); 
		List<ResourceSet> right = new ArrayList<>(); 
		
		right.add(new ResourceSet(new ResourceType[] {ResourceType.GLASS}));
		left.add(new ResourceSet(new ResourceType[] { ResourceType.PAPER}));
		
		int maxTradeCost = 10; 
		ResourceCost remainingResourceCost = new ResourceCost(new ResourceType[] {ResourceType.GLASS, ResourceType.PAPER}); 
		TradingProviderList trades = new TradingProviderList();
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(4, eval.findMinCost());
		assertEquals(2, Math.max(eval.getLeftCost(), eval.getRightCost()));
		assertEquals(2, Math.min(eval.getLeftCost(), eval.getRightCost()));
	}
	
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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(2, eval.findMinCost());
		assertEquals(1, Math.max(eval.getLeftCost(), eval.getRightCost()));
		assertEquals(1, Math.min(eval.getLeftCost(), eval.getRightCost()));
	}

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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(3, eval.findMinCost());
		assertEquals(2, eval.getLeftCost());
		assertEquals(1, eval.getRightCost());
	}	
	
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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(1, eval.findMinCost());
		assertEquals(1, eval.getLeftCost());
		assertEquals(0, eval.getRightCost());
	}
	
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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(1, eval.findMinCost());
	}
	

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
		
		TradingResourceEvaluator eval = new TradingResourceEvaluator(board, left, right, maxTradeCost, remainingResourceCost, trades);
		assertEquals(2, eval.findMinCost());
		
	}
	
}
