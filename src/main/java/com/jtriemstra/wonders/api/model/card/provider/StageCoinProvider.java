package com.jtriemstra.wonders.api.model.card.provider;

import java.util.List;

import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;

public class StageCoinProvider extends CoinProvider {
	private int coinsPer;
	private List<Player> players;
	
	public StageCoinProvider(int coinsPer, List<Player> players) {
		this.coinsPer = coinsPer;
		this.players = players;
	}

	@Override
	public int getCoins() {
		int coins = 0;
		for (Player p : players) {
			coins += coinsPer * p.getNumberOfBuiltStages();
		}
		return coins;
	}

}
