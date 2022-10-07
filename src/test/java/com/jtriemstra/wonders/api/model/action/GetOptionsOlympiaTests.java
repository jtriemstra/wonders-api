package com.jtriemstra.wonders.api.model.action;

import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.DiscardPile;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Palace;
import com.jtriemstra.wonders.api.model.card.StonePit;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, TestBase.TestStateConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetOptionsOlympiaTests extends TestBase {
	
	public HashSet<Integer> firstAgeUsed(){
		HashSet<Integer> h = new HashSet<>();
		h.add(1);
		return h;
	}
	
	@Test
	public void when_havent_used_can_play_expensive_card_for_free() {
		setupTest();
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));

		testPlayer.discardHand(Mockito.mock(DiscardPile.class));
		testPlayer.receiveCard(new Palace(3,3));
		
		testPlayer.addNextAction(new GetOptionsOlympia(new HashSet<>()));
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("discard;playFree", testPlayer.getNextAction().toString());
		
	}
	
	//TODO: (low) maybe nice to change this so if "play" is an option "playFree" is hidden. Should probably take into account trading costs as well - allow both if you'd have to pay.
	@Test
	public void when_havent_used_can_play_cheap_card_for_free() {
		setupTest();
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));

		testPlayer.discardHand(Mockito.mock(DiscardPile.class));
		testPlayer.receiveCard(new StonePit(3,1));
		
		testPlayer.addNextAction(new GetOptionsOlympia(new HashSet<>()));
						
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("play;discard;playFree", testPlayer.getNextAction().toString());		
	}
	
	@Test
	public void when_used_cannnot_play_for_free() {
		setupTest();
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));

		testPlayer.discardHand(Mockito.mock(DiscardPile.class));
		testPlayer.receiveCard(new Palace(3,3));
		
		testPlayer.addNextAction(new GetOptionsOlympia(firstAgeUsed()));
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("discard", testPlayer.getNextAction().toString());		
	}
	
	@Test
	public void when_card_already_played_cannot_play_again() {
		setupTest(new Palace(3,3));
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));

		testPlayer.discardHand(Mockito.mock(DiscardPile.class));
		testPlayer.receiveCard(new Palace(3,3));
		
		testPlayer.addNextAction(new GetOptionsOlympia(firstAgeUsed()));
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("discard", testPlayer.getNextAction().toString());		
	}
}
