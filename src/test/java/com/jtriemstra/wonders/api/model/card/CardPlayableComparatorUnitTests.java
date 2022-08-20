package com.jtriemstra.wonders.api.model.card;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.resource.TradingResourceEvaluator2.TradeCost;

public class CardPlayableComparatorUnitTests {

	@Test
	public void when_cards_differ_by_type_should_sort_by_that() {
		CardPlayable c1 = Mockito.mock(CardPlayable.class);
		CardPlayable c2 = Mockito.mock(CardPlayable.class);
		
		Mockito.doReturn("army").when(c1).getCardType();
		Mockito.doReturn("science").when(c2).getCardType();
		
		CardPlayableComparator testObject = new CardPlayableComparator();
		
		Assertions.assertTrue(0 > testObject.compare(c1, c2));
		Assertions.assertTrue(0 < testObject.compare(c2, c1));
	}

	@Test
	public void when_cards_differ_by_status_should_sort_by_that() {
		CardPlayable c1 = Mockito.mock(CardPlayable.class);
		CardPlayable c2 = Mockito.mock(CardPlayable.class);
		
		Mockito.doReturn("science").when(c1).getCardType();
		Mockito.doReturn(Status.OK).when(c1).getStatus();
		Mockito.doReturn("science").when(c2).getCardType();
		Mockito.doReturn(Status.ERR_COINS).when(c2).getStatus();
		
		CardPlayableComparator testObject = new CardPlayableComparator();
		
		Assertions.assertTrue(0 > testObject.compare(c1, c2));
		Assertions.assertTrue(0 < testObject.compare(c2, c1));
	}

	@Test
	public void when_cards_differ_by_cost_should_sort_by_that() {
		CardPlayable c1 = Mockito.mock(CardPlayable.class);
		CardPlayable c2 = Mockito.mock(CardPlayable.class);
		
		Mockito.doReturn("science").when(c1).getCardType();
		Mockito.doReturn(Status.OK).when(c1).getStatus();
		Mockito.doReturn(1).when(c1).getBankCost();
		Mockito.doReturn("science").when(c2).getCardType();
		Mockito.doReturn(Status.OK).when(c2).getStatus();
		Mockito.doReturn(2).when(c2).getBankCost();
		
		CardPlayableComparator testObject = new CardPlayableComparator();
		
		Assertions.assertTrue(0 > testObject.compare(c1, c2));
		Assertions.assertTrue(0 < testObject.compare(c2, c1));
	}

	@Test
	public void when_cards_differ_by_trade_cost_should_sort_by_that() {
		CardPlayable c1 = Mockito.mock(CardPlayable.class);
		CardPlayable c2 = Mockito.mock(CardPlayable.class);
		TradeCost tc1 = Mockito.mock(TradeCost.class);
		TradeCost tc2 = Mockito.mock(TradeCost.class);
		TradeCost tc3 = Mockito.mock(TradeCost.class);
		
		Mockito.doReturn(Map.of("Left",1,"Right",0)).when(tc1).getKnownCostsBySource();
		Mockito.doReturn(Map.of("Left",2,"Right",1)).when(tc2).getKnownCostsBySource();
		Mockito.doReturn(Map.of("Left",4,"Right",4)).when(tc3).getKnownCostsBySource();
		
		Mockito.doReturn("science").when(c1).getCardType();
		Mockito.doReturn(Status.OK).when(c1).getStatus();
		Mockito.doReturn(1).when(c1).getBankCost();
		Mockito.doReturn(List.of(tc1,tc2)).when(c1).getCostOptions();
		Mockito.doReturn("science").when(c2).getCardType();
		Mockito.doReturn(Status.OK).when(c2).getStatus();
		Mockito.doReturn(1).when(c2).getBankCost();
		Mockito.doReturn(List.of(tc3)).when(c2).getCostOptions();
		
		CardPlayableComparator testObject = new CardPlayableComparator();
		
		Assertions.assertTrue(0 > testObject.compare(c1, c2));
		Assertions.assertTrue(0 < testObject.compare(c2, c1));
	}
}

