package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.TechResourceCard;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Praxiteles extends LeaderCard {
	
	private PlayerSourceStrategy playerSource = new PlayerOnlyStrategy();

	@Override
	public String getName() {
		return "Praxiteles";
	}
	
	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new CardVPProvider(2, TechResourceCard.class, players, VictoryPointType.LEADER));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 3;
	}

	@Override
	public String getHelp() {
		return "This card grants 2 victory points for every gray card you have played at the end of the game";
	}	
}
