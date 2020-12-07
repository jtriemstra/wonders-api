package com.jtriemstra.wonders.api.model.card.provider;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;

public class CardCoinProvider extends CoinProvider {
	
	private Class targetType;
	private int coinsPer;
	private List<Player> targets;
	
	public CardCoinProvider(int coinsPer, Class clazz, List<Player> targets) {
		this.targetType = clazz;
		this.coinsPer = coinsPer;
		this.targets = targets;
	}

	@Override
	public int getCoins() {
		int coins = 0;
		for (Player p : targets) {
			coins += coinsPer * p.getCardsOfTypeFromBoard(targetType).size();
		}
		return coins;
	}
	
}