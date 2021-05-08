package com.jtriemstra.wonders.api.model.deck;

import java.util.ArrayList;
import java.util.List;

import com.jtriemstra.wonders.api.model.card.BuildersGuild;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CraftsmensGuild;
import com.jtriemstra.wonders.api.model.card.MagistratesGuild;
import com.jtriemstra.wonders.api.model.card.PhilosophersGuild;
import com.jtriemstra.wonders.api.model.card.ScientistsGuild;
import com.jtriemstra.wonders.api.model.card.ShipownersGuild;
import com.jtriemstra.wonders.api.model.card.SpiesGuild;
import com.jtriemstra.wonders.api.model.card.StrategistsGuild;
import com.jtriemstra.wonders.api.model.card.TradersGuild;
import com.jtriemstra.wonders.api.model.card.WorkersGuild;

public class GuildCardFactoryBasic implements CardFactory {
	public Card[] getAllCards() {
		Card[] cards = new Card[] {
				new MagistratesGuild(3,3),
				new PhilosophersGuild(3,3),
				new SpiesGuild(3,3),
				new ShipownersGuild(3,3),
				//new TradersGuild(3,3),
				//new CraftsmensGuild(3,3),
				//new WorkersGuild(3,3),
				//new BuildersGuild(3,3),
				//new StrategistsGuild(3,3),
				new ScientistsGuild(3,3)
		};
		return cards;
	}
}
