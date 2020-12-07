package com.jtriemstra.wonders.api.model.resource;

import com.jtriemstra.wonders.api.model.Player;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BankPayment implements Payment {
	private int coins;
	private Player sender;
	
	@Override
	public void execute() {
		sender.gainCoins(coins * -1);		
	}
}
