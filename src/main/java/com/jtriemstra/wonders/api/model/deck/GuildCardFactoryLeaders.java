package com.jtriemstra.wonders.api.model.deck;

import java.util.Arrays;

import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.CourtesansGuild;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GuildCardFactoryLeaders implements GuildCardFactory {
	private GuildCardFactory innerFactory;

	@Override
	public Card[] getGuilds() {
		Card[] inner = innerFactory.getGuilds();
		Card[] leaders = new Card[] {
				new CourtesansGuild(3,3)
		};
		
		Card[] result = Arrays.copyOf(inner, inner.length + leaders.length);
		System.arraycopy(leaders, 0, result, inner.length, leaders.length);
		
		return result;
	}
	
	
}
