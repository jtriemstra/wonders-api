package com.jtriemstra.wonders.api.model.resource;

import com.jtriemstra.wonders.api.model.Player;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TradingPayment implements Payment {
	private int coins;
	private Player sender;
	private Player recipient;
	
	@Override
	public void execute() {
		sender.gainCoins(coins * -1);
		recipient.gainCoins(coins);
	}
}
