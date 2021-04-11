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
	
	public AgeDeck(int numberOfPlayer, int age) {
		for (Card c : getFull()) {
			if (c.getMinPlayers() <= numberOfPlayer && c.getAge() == age) {
				deck.add(c);
			}
		}	
		
		if (age == 3) {
			List<Card> guilds = Arrays.asList(getGuilds());
			ArrayList<Card> guilds2 = new ArrayList<Card>(guilds);
			Random r = new Random();
			for (int i=0; i<numberOfPlayer + 2; i++) {
				deck.add(guilds2.remove(r.nextInt(guilds2.size())));
			}
		}
	}
	
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
	
	private static Card[] getFull() {
		return new Card[] {
			new StonePit(3, 1),
			new Altar(3, 1),
			new Theater(3, 1),
			new Baths(3, 1),
			new Altar(5, 1),
			new Theater(6, 1),
			new Baths(7, 1),
			new Pawnshop(4, 1),
			new Pawnshop(7, 1),
			new StonePit(5, 1),
			new LumberYard(3, 1),
			new LumberYard(4, 1),
			new OreVein(3, 1),
			new OreVein(4, 1),
			new ClayPool(3, 1),
			new ClayPool(5, 1),
			new ClayPit(3, 1),
			new ForestCave( 5, 1),
			new Excavation(4, 1),
			new TimberYard(3, 1),
			new TreeFarm(6, 1),
			new Mine(6, 1),
			new Press(3, 1),
			new Press(6, 1),
			new Glassworks(6, 1),
			new Loom(6, 1),
			new Glassworks(3, 1),
			new Loom(3, 1),
			new Stockade(3, 1),
			new Barracks(3, 1),
			new GuardTower(3, 1),
			new Stockade(7, 1),
			new Barracks(5, 1),
			new GuardTower(4, 1),
			new Apothecary(3, 1),
			new Scriptorium(3, 1),
			new Workshop(3, 1),
			new Apothecary(5, 1),
			new Scriptorium(4, 1),
			new Workshop(7, 1),
			new Tavern(4, 1),
			new WestTradingPost(3, 1),
			new EastTradingPost(3, 1),
			new Marketplace(3, 1),
			new Tavern(5, 1),
			new WestTradingPost(7, 1),
			new EastTradingPost(7, 1),
			new Marketplace(6, 1),
			new Tavern(7, 1),
			new Stables(3, 2),
			new Walls(3, 2),
			new ArcheryRange(3, 2),
			new TrainingGround(4, 2),
			new Stables(5, 2),
			new Walls(7, 2),
			new ArcheryRange(6, 2),
			new TrainingGround(6, 2),
			new TrainingGround(7, 2),
			new Library(3,2),
			new School(3,2),
			new Dispensary(3,2),
			new Laboratory(3,2),
			new Library(6,2),
			new School(7,2),
			new Dispensary(4,2),
			new Laboratory(5,2),
			new Bazar(4,2),
			new Vineyard(3,2),
			new Forum(3,2),
			new Caravansery(3,2),
			new Bazar(7,2),
			new Vineyard(6,2),
			new Forum(6,2),
			new Caravansery(6,2),
			new Forum(7,2),
			new Caravansery(5,2),
			new Statue(3, 2),
			new Temple(3, 2),
			new Aqueduct(3, 2),
			new Courthouse(3, 2),
			new Statue(7, 2),
			new Temple(6, 2),
			new Aqueduct(7, 2),
			new Courthouse(5, 2),
			new Press(3, 2),
			new Press(5, 2),
			new Glassworks(5, 2),
			new Loom(5, 2),
			new Glassworks(3, 2),
			new Loom(3, 2),
			new Brickyard(3,2),
			new Quarry(3,2),
			new Sawmill(3,2),
			new Foundry(3,2),
			new Brickyard(4,2),
			new Quarry(4,2),
			new Sawmill(4,2),
			new Foundry(4,2),
			new Arena(3,3),
			new Haven(3,3),
			new Lighthouse(3,3),
			new ChamberOfCommerce(6,3),
			new Arena(7,3),
			new Haven(4,3),
			new Lighthouse(6,3),
			new ChamberOfCommerce(4,3),
			new Arena(5,3),
			new University(4,3),
			new Academy(7,3),
			new Lodge(6,3),
			new Study(5,3),
			new Observatory(7,3),
			new University(3,3),
			new Academy(3,3),
			new Lodge(3,3),
			new Study(3,3),
			new Observatory(3,3),
			new Fortifications(3,3),
			new Circus(5,3),
			new Arsenal(3,3),
			new SiegeWorkshop(3,3),
			new Fortifications(7,3),
			new Circus(4,3),
			new Arsenal(4,3),
			new SiegeWorkshop(5,3),
			new Circus(6,3),
			new Arsenal(7,3),
			new Gardens(3,3),
			new Senate(3,3),
			new TownHall(3,3),
			new Pantheon(3,3),
			new Palace(3,3),
			new Gardens(4,3),
			new Senate(5,3),
			new TownHall(5,3),
			new Pantheon(6,3),
			new Palace(7,3),
			new TownHall(6,3)
		};				
	}
	
	private Card[] getGuilds() {
		return new Card[] {
				new MagistratesGuild(3,3),
				new PhilosophersGuild(3,3),
				new SpiesGuild(3,3),
				new ShipownersGuild(3,3),
				new TradersGuild(3,3),
				new CraftsmensGuild(3,3),
				new WorkersGuild(3,3),
				new BuildersGuild(3,3),
				new StrategistsGuild(3,3),
				new ScientistsGuild(3,3)
		};
	}
	
	//TODO: this is only to support testing...better way?
	public int size() {
		return deck.size();
	}
}
