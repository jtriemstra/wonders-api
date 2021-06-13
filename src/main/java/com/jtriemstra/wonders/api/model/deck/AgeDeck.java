package com.jtriemstra.wonders.api.model.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jtriemstra.wonders.api.model.card.Academy;
import com.jtriemstra.wonders.api.model.card.Altar;
import com.jtriemstra.wonders.api.model.card.Apothecary;
import com.jtriemstra.wonders.api.model.card.Aqueduct;
import com.jtriemstra.wonders.api.model.card.ArcheryRange;
import com.jtriemstra.wonders.api.model.card.Arena;
import com.jtriemstra.wonders.api.model.card.Arsenal;
import com.jtriemstra.wonders.api.model.card.Barracks;
import com.jtriemstra.wonders.api.model.card.Baths;
import com.jtriemstra.wonders.api.model.card.Bazar;
import com.jtriemstra.wonders.api.model.card.Brickyard;
import com.jtriemstra.wonders.api.model.card.BuildersGuild;
import com.jtriemstra.wonders.api.model.card.Caravansery;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.ChamberOfCommerce;
import com.jtriemstra.wonders.api.model.card.Circus;
import com.jtriemstra.wonders.api.model.card.ClayPit;
import com.jtriemstra.wonders.api.model.card.ClayPool;
import com.jtriemstra.wonders.api.model.card.Courthouse;
import com.jtriemstra.wonders.api.model.card.CraftsmensGuild;
import com.jtriemstra.wonders.api.model.card.Dispensary;
import com.jtriemstra.wonders.api.model.card.EastTradingPost;
import com.jtriemstra.wonders.api.model.card.Excavation;
import com.jtriemstra.wonders.api.model.card.ForestCave;
import com.jtriemstra.wonders.api.model.card.Fortifications;
import com.jtriemstra.wonders.api.model.card.Forum;
import com.jtriemstra.wonders.api.model.card.Foundry;
import com.jtriemstra.wonders.api.model.card.Gardens;
import com.jtriemstra.wonders.api.model.card.Glassworks;
import com.jtriemstra.wonders.api.model.card.GuardTower;
import com.jtriemstra.wonders.api.model.card.Haven;
import com.jtriemstra.wonders.api.model.card.Laboratory;
import com.jtriemstra.wonders.api.model.card.Library;
import com.jtriemstra.wonders.api.model.card.Lighthouse;
import com.jtriemstra.wonders.api.model.card.Lodge;
import com.jtriemstra.wonders.api.model.card.Loom;
import com.jtriemstra.wonders.api.model.card.LumberYard;
import com.jtriemstra.wonders.api.model.card.MagistratesGuild;
import com.jtriemstra.wonders.api.model.card.Marketplace;
import com.jtriemstra.wonders.api.model.card.Mine;
import com.jtriemstra.wonders.api.model.card.Observatory;
import com.jtriemstra.wonders.api.model.card.OreVein;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.Pantheon;
import com.jtriemstra.wonders.api.model.card.Pawnshop;
import com.jtriemstra.wonders.api.model.card.PhilosophersGuild;
import com.jtriemstra.wonders.api.model.card.Press;
import com.jtriemstra.wonders.api.model.card.Quarry;
import com.jtriemstra.wonders.api.model.card.Sawmill;
import com.jtriemstra.wonders.api.model.card.School;
import com.jtriemstra.wonders.api.model.card.ScientistsGuild;
import com.jtriemstra.wonders.api.model.card.Scriptorium;
import com.jtriemstra.wonders.api.model.card.Senate;
import com.jtriemstra.wonders.api.model.card.ShipownersGuild;
import com.jtriemstra.wonders.api.model.card.SiegeWorkshop;
import com.jtriemstra.wonders.api.model.card.SpiesGuild;
import com.jtriemstra.wonders.api.model.card.Stables;
import com.jtriemstra.wonders.api.model.card.Statue;
import com.jtriemstra.wonders.api.model.card.Stockade;
import com.jtriemstra.wonders.api.model.card.StonePit;
import com.jtriemstra.wonders.api.model.card.StrategistsGuild;
import com.jtriemstra.wonders.api.model.card.Study;
import com.jtriemstra.wonders.api.model.card.Tavern;
import com.jtriemstra.wonders.api.model.card.Temple;
import com.jtriemstra.wonders.api.model.card.Theater;
import com.jtriemstra.wonders.api.model.card.TimberYard;
import com.jtriemstra.wonders.api.model.card.TownHall;
import com.jtriemstra.wonders.api.model.card.TradersGuild;
import com.jtriemstra.wonders.api.model.card.TrainingGround;
import com.jtriemstra.wonders.api.model.card.TreeFarm;
import com.jtriemstra.wonders.api.model.card.University;
import com.jtriemstra.wonders.api.model.card.Vineyard;
import com.jtriemstra.wonders.api.model.card.Walls;
import com.jtriemstra.wonders.api.model.card.WestTradingPost;
import com.jtriemstra.wonders.api.model.card.WorkersGuild;
import com.jtriemstra.wonders.api.model.card.Workshop;

public class AgeDeck extends Deck {
	
	private Logger logger3 = LoggerFactory.getLogger("tests");
	
	public AgeDeck(int numberOfPlayer, int age, CardFactory ageCardFactory, CardFactory guildFactory) {
		for (Card c : ageCardFactory.getAllCards()) {
			if (c.getMinPlayers() <= numberOfPlayer && c.getAge() == age) {
				deck.add(c);
			}
		}	
		
		if (age == 3) {
			List<Card> guilds = Arrays.asList(guildFactory.getAllCards());
			ArrayList<Card> guilds2 = new ArrayList<Card>(guilds);
			Random r = new Random();
			for (int i=0; i<numberOfPlayer + 2; i++) {
				deck.add(guilds2.remove(r.nextInt(guilds2.size())));
			}
		}
	}
		
	//TODO: this is only to support testing...better way?
	public int size() {
		return deck.size();
	}
}
