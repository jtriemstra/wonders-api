package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.ArmyCard;
import com.jtriemstra.wonders.api.model.card.ScienceCard;
import com.jtriemstra.wonders.api.model.card.VictoryCard;
import com.jtriemstra.wonders.api.model.card.provider.LambdaVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Justinian extends LeaderCard {

	private PlayerSourceStrategy playerSource = new PlayerOnlyStrategy();
	
	@Override
	public String getName() {
		return "Justinian";
	}

	@Override
	public void play(IPlayer player, Game game) {
		List<IPlayer> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new LambdaVPProvider(3,players, p -> {
			int blue = p.getCardsOfTypeFromBoard(VictoryCard.class).size();
			int green = p.getCardsOfTypeFromBoard(ScienceCard.class).size();
			int red = p.getCardsOfTypeFromBoard(ArmyCard.class).size();
			return Math.min(Math.min(blue, green), red);
		}, VictoryPointType.LEADER));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 3;
	}

	@Override
	public String getHelp() {
		return "This card grants 3 victory points for each set of a red, green, and blue card you have played at the end of the game";
	}
}
