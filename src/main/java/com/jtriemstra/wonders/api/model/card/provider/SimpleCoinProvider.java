package com.jtriemstra.wonders.api.model.card.provider;

public class SimpleCoinProvider extends CoinProvider {
	private int coins;
	
	public SimpleCoinProvider(int coins) {
		this.coins = coins;
	}

	@Override
	public int getCoins() {
		return coins;
	}
}
