package com.jtriemstra.wonders.api.model.card;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;

import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;

public class CardPlayableComparator implements Comparator<CardPlayable> {

	@Override
	public int compare(CardPlayable o1, CardPlayable o2) {
		if (o1.getCardType().equals(o2.getCardType())) {
			if ((o1.getStatus() == Status.OK && o2.getStatus() == Status.OK)
			|| (o1.getStatus() != Status.OK && o2.getStatus() != Status.OK)) {
				int minTradeCost1 = getMinTradeCost(o1);
				int minTradeCost2 = getMinTradeCost(o2);
				return Integer.compare(minTradeCost1 + o1.getBankCost(), minTradeCost2 + o2.getBankCost());
			}
			else {
				if (o1.getStatus() == Status.OK) {
					return -1;
				}
				else {
					return 1;
				}
			}
		}
		else {
			return o1.getCardType().compareTo(o2.getCardType());
		}
	}

	public int getMinTradeCost(CardPlayable o) {
		OptionalInt minTradeCost1 = o
				.getCostOptions()
				.stream()
				.mapToInt(
					tc -> tc.getKnownCostsBySource()
							.values()
							.stream()
							.mapToInt(
								v -> v.intValue()
							)
							.sum()
				)
				.min();
		return minTradeCost1.isPresent() ? minTradeCost1.getAsInt() : 0;
	}
}
