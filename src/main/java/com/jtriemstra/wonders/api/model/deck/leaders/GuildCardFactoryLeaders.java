package com.jtriemstra.wonders.api.model.deck.leaders;

import java.util.Arrays;

import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.ArchitectsGuild;
import com.jtriemstra.wonders.api.model.card.leaders.DiplomatsGuild;
import com.jtriemstra.wonders.api.model.card.leaders.GamersGuild;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GuildCardFactoryLeaders implements CardFactory {
	private CardFactory innerFactory;

	@Override
	public Card[] getAllCards() {
		Card[] inner = innerFactory.getAllCards();
		Card[] leaders = new Card[] {
				new GamersGuild(3,3),
				new DiplomatsGuild(3,3),
				new ArchitectsGuild(3,3)
		};
		
		Card[] result = Arrays.copyOf(inner, inner.length + leaders.length);
		System.arraycopy(leaders, 0, result, inner.length, leaders.length);
		
		return result;
	}
	
	
}
