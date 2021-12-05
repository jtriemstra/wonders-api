package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

public class PlayerVictoryPointUnitTests {
	@Test
	public void when_set_of_science_then_seven_points() {
		IPlayer p = new Player("test1", new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), new NotificationService());
		p.addScienceProvider(() -> {return new Science(ScienceType.COMPASS);});
		p.addScienceProvider(() -> {return new Science(ScienceType.TABLET);});
		p.addScienceProvider(() -> {return new Science(ScienceType.GEAR);});
		
		Assertions.assertEquals(10, p.getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}
	
	@Test
	public void when_set_of_science_plus_2_then_18_points() {
		IPlayer p = new Player("test1", new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), new NotificationService());
		p.addScienceProvider(() -> {return new Science(ScienceType.COMPASS);});
		p.addScienceProvider(() -> {return new Science(ScienceType.TABLET);});
		p.addScienceProvider(() -> {return new Science(ScienceType.GEAR);});
		p.addScienceProvider(() -> {return new Science(ScienceType.GEAR);});
		p.addScienceProvider(() -> {return new Science(ScienceType.GEAR);});
		
		Assertions.assertEquals(18, p.getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}
	
	@Test
	public void when_list_of_victory_card_then_sum() {
		IPlayer p = new Player("test1", new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), new NotificationService());
		p.addVPProvider(new SimpleVPProvider(4, VictoryPointType.VICTORY));
		p.addVPProvider(new SimpleVPProvider(6, VictoryPointType.VICTORY));
		
		Assertions.assertEquals(10, p.getFinalVictoryPoints().get(VictoryPointType.VICTORY));
	}
	
	@Test
	public void when_magistrates_guild_and_neighbors_have_zero_then_zero() {
		IPlayer p1 = new Player("test1", new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), new NotificationService());
		Player p2 = new Player("test2", new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), new NotificationService());
		Player p3 = new Player("test3", new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), new NotificationService());
		
		p1.addVPProvider(new CardVPProvider(1, VictoryCard.class, Arrays.asList(p2, p3), VictoryPointType.GUILD));
		
		Assertions.assertEquals(0, p1.getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}

	@Test
	public void when_magistrates_guild_and_neighbors_have_cards_then_two() {
		CardList cl1 = new CardList();
		cl1.add(new Palace(3,3));
		CardList cl2 = new CardList();
		cl2.add(new Pantheon(3,3));
		IPlayer p1 = new Player("test1", new ActionList(), new ArrayList<>(), new ArrayList<>(), new CardList(), new NotificationService());
		Player p2 = new Player("test2", new ActionList(), new ArrayList<>(), new ArrayList<>(), cl1, new NotificationService());
		Player p3 = new Player("test3", new ActionList(), new ArrayList<>(), new ArrayList<>(), cl2, new NotificationService());
		
		p1.addVPProvider(new CardVPProvider(1, VictoryCard.class, Arrays.asList(p2, p3), VictoryPointType.GUILD));
		
		
		Assertions.assertEquals(2, p1.getFinalVictoryPoints().get(VictoryPointType.GUILD));
	}
	
}
