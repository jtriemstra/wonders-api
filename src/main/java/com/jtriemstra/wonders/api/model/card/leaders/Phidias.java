package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.NaturalResourceCard;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Phidias extends LeaderCard {
	
	private PlayerSourceStrategy playerSource = new PlayerOnlyStrategy();

	@Override
	public String getName() {
		return "Phidias";
	}
	
	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new CardVPProvider(1, NaturalResourceCard.class, players, VictoryPointType.LEADER));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 1;
	}	
}
