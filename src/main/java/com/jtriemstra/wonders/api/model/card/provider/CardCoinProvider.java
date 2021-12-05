package com.jtriemstra.wonders.api.model.card.provider;

import java.util.List;

import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;

public class CardCoinProvider extends CoinProvider {
	
	private Class targetType;
	private int coinsPer;
	private List<IPlayer> targets;
	
	public CardCoinProvider(int coinsPer, Class clazz, List<IPlayer> targets) {
		this.targetType = clazz;
		this.coinsPer = coinsPer;
		this.targets = targets;
	}

	@Override
	public int getCoins() {
		int coins = 0;
		for (IPlayer p : targets) {
			coins += coinsPer * p.getCardsOfTypeFromBoard(targetType).size();
		}
		return coins;
	}
	
}