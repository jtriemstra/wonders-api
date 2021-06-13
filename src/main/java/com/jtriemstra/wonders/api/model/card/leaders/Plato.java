package com.jtriemstra.wonders.api.model.card.leaders;

import java.util.List;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerOnlyStrategy;
import com.jtriemstra.wonders.api.model.PlayerSourceStrategy;
import com.jtriemstra.wonders.api.model.card.ArmyCard;
import com.jtriemstra.wonders.api.model.card.CommerceCard;
import com.jtriemstra.wonders.api.model.card.GuildCard;
import com.jtriemstra.wonders.api.model.card.NaturalResourceCard;
import com.jtriemstra.wonders.api.model.card.ScienceCard;
import com.jtriemstra.wonders.api.model.card.TechResourceCard;
import com.jtriemstra.wonders.api.model.card.VictoryCard;
import com.jtriemstra.wonders.api.model.card.provider.LambdaVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

public class Plato extends LeaderCard {

	private PlayerSourceStrategy playerSource = new PlayerOnlyStrategy();;
	
	@Override
	public String getName() {
		return "Plato";
	}

	@Override
	public void play(Player player, Game game) {
		List<Player> players = playerSource.getPlayers(player, game);
		
		player.addVPProvider(new LambdaVPProvider(7,players, p -> {
			int blue = p.getCardsOfTypeFromBoard(VictoryCard.class).size();
			int green = p.getCardsOfTypeFromBoard(ScienceCard.class).size();
			int red = p.getCardsOfTypeFromBoard(ArmyCard.class).size();
			int yellow = p.getCardsOfTypeFromBoard(CommerceCard.class).size();
			int brown = p.getCardsOfTypeFromBoard(NaturalResourceCard.class).size();
			int gray = p.getCardsOfTypeFromBoard(TechResourceCard.class).size();
			int purple = p.getCardsOfTypeFromBoard(GuildCard.class).size();
			return Math.min(Math.min(Math.min(Math.min(Math.min(Math.min(purple, gray), brown), yellow), blue), green), red);
		}, VictoryPointType.LEADER));
		
		super.play(player, game);
	}
	
	@Override
	public int getCoinCost() {
		return 4;
	}

	@Override
	public String getHelp() {
		return "This card grants 7 victory points for each set of all 7 colors you have played at the end of the game";
	}
}
