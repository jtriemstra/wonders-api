package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.UnitTestCaseBuilder;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.Pantheon;
import com.jtriemstra.wonders.api.model.card.Science;
import com.jtriemstra.wonders.api.model.card.ScienceType;
import com.jtriemstra.wonders.api.model.card.VictoryCard;
import com.jtriemstra.wonders.api.model.card.provider.CardVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.SimpleVPProvider;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.notifications.NotificationService;
import com.jtriemstra.wonders.api.state.StateService;

public class PlayerVictoryPointUnitTests {
	@Test
	public void when_set_of_science_then_seven_points() {
		IPlayer p =
			UnitTestCaseBuilder.create()
			.withPlayerScienceProviders("test1",
					() -> {return new Science(ScienceType.COMPASS);},
					() -> {return new Science(ScienceType.TABLET);},
					() -> {return new Science(ScienceType.GEAR);})
			.getPlayer("test1");
		
		Assertions.assertEquals(10, p.getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}
	
	@Test
	public void when_set_of_science_plus_2_then_18_points() {
		IPlayer p =
				UnitTestCaseBuilder.create()
				.withPlayerScienceProviders("test1",
						() -> {return new Science(ScienceType.COMPASS);},
						() -> {return new Science(ScienceType.TABLET);},
						() -> {return new Science(ScienceType.GEAR);},
						() -> {return new Science(ScienceType.GEAR);},
						() -> {return new Science(ScienceType.GEAR);})
				.getPlayer("test1");
				
		Assertions.assertEquals(18, p.getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}
	
	@Test
	public void when_list_of_victory_card_then_sum() {
		IPlayer p = UnitTestCaseBuilder.create()
			.withPlayerVPProviders("test1",
					new SimpleVPProvider(4, VictoryPointType.VICTORY),
					new SimpleVPProvider(6, VictoryPointType.VICTORY))
			.getPlayer("test1");
				
		Assertions.assertEquals(10, p.getFinalVictoryPoints().get(VictoryPointType.VICTORY));
	}
	
	@Test
	public void when_magistrates_guild_and_neighbors_have_zero_then_zero() {
		UnitTestCaseBuilder testBuilder = UnitTestCaseBuilder.create().withPlayerNames("test1","test2","test3");
		
		IPlayer p1 = testBuilder.getPlayer("test1");
		IPlayer p2 = testBuilder.getPlayer("test2");
		IPlayer p3 = testBuilder.getPlayer("test3");
		
		p1.addVPProvider(new CardVPProvider(1, VictoryCard.class, Arrays.asList(p2, p3), VictoryPointType.GUILD));
		
		Assertions.assertEquals(0, p1.getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}

	@Test
	public void when_magistrates_guild_and_neighbors_have_cards_then_two() {
		UnitTestCaseBuilder testBuilder = UnitTestCaseBuilder
				.create()
				.withPlayerNames("test1","test2","test3")
				.withPlayerCardsOnBoard("test2", new Palace(3,3))
				.withPlayerCardsOnBoard("test3", new Pantheon(3,3))
				;
		
		IPlayer p1 = testBuilder.getPlayer("test1");
		IPlayer p2 = testBuilder.getPlayer("test2");
		IPlayer p3 = testBuilder.getPlayer("test3");
		
		p1.addVPProvider(new CardVPProvider(1, VictoryCard.class, Arrays.asList(p2, p3), VictoryPointType.GUILD));
		
		
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}
	
}
