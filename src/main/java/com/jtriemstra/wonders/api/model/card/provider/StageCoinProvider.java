package com.jtriemstra.wonders.api.model.card.provider;

import java.util.List;

import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.card.Card;

public class StageCoinProvider extends CoinProvider {
	private int coinsPer;
	private List<IPlayer> players;
	
	public StageCoinProvider(int coinsPer, List<IPlayer> players) {
		this.coinsPer = coinsPer;
		this.players = players;
	}

	@Override
	public int getCoins() {
		int coins = 0;
		for (IPlayer p : players) {
			coins += coinsPer * p.getNumberOfBuiltStages();
		}
		return coins;
	}

}
