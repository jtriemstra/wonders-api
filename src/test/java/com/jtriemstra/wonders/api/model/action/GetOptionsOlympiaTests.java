package com.jtriemstra.wonders.api.model.action;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.StonePit;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class GetOptionsOlympiaTests extends TestBase {
	
	public HashSet<Integer> firstAgeUsed(){
		HashSet<Integer> h = new HashSet<>();
		h.add(1);
		return h;
	}
	
	@Test
	public void when_havent_used_can_play_expensive_card_for_free() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayerWithCard(new Palace(3,3), g);
		
		Mockito.doReturn(1).when(g).getCurrentAge();
		
		p1.addNextAction(new GetOptionsOlympia(new HashSet<>()));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("discard;playFree", p1.getNextAction().toString());
		
	}
	
	//TODO: maybe nice to change this so if "play" is an option "playFree" is hidden. Should probably take into account trading costs as well - allow both if you'd have to pay.
	@Test
	public void when_havent_used_can_play_cheap_card_for_free() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayerWithCard(new StonePit(3,1), g);
		
		Mockito.doReturn(1).when(g).getCurrentAge();
		
		p1.addNextAction(new GetOptionsOlympia(new HashSet<>()));
						
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("play;discard;playFree", p1.getNextAction().toString());		
	}
	
	@Test
	public void when_used_cannnot_play_for_free() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayerWithCard(new Palace(3,3), g);
		
		Mockito.doReturn(1).when(g).getCurrentAge();
		
		p1.addNextAction(new GetOptionsOlympia(firstAgeUsed()));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("discard", p1.getNextAction().toString());		
	}
	

	@Test
	public void when_used_in_one_age_can_use_later() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayerWithCard(new Palace(3,3), g);
		
		Mockito.doReturn(2).when(g).getCurrentAge();
		
		p1.addNextAction(new GetOptionsOlympia(firstAgeUsed()));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("discard;playFree", p1.getNextAction().toString());		
	}
	

	@Test
	public void when_card_already_played_cannot_play_again() {
		Game g = Mockito.spy(setUpGame());
		Player p1 = setUpPlayerWithCard(new Palace(3,3), g);
		fakePreviousCard(p1, new Palace(3,3), g);
		
		Mockito.doReturn(2).when(g).getCurrentAge();
		
		p1.addNextAction(new GetOptionsOlympia(firstAgeUsed()));
				
		OptionsRequest r = new OptionsRequest();
		
		p1.doAction(r, g);
		
		Assertions.assertEquals("discard", p1.getNextAction().toString());		
	}
}
