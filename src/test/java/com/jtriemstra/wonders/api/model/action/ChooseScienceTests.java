package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.ChooseScienceRequest;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Academy;
import com.jtriemstra.wonders.api.model.card.Apothecary;
import com.jtriemstra.wonders.api.model.card.Dispensary;
import com.jtriemstra.wonders.api.model.card.Laboratory;
import com.jtriemstra.wonders.api.model.card.Library;
import com.jtriemstra.wonders.api.model.card.ScienceType;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ChooseScienceTests extends TestBase {
	
	@Test
	public void when_choosing_science_then_added_to_player() {
		setupTest();
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		ChooseScience cs = new ChooseScience();
		testPlayer.addNextAction(cs);
		
		Assertions.assertFalse(gameWithThreePlayers.getFlow().isFinalTurn());
		
		ChooseScienceRequest r = new ChooseScienceRequest();
		r.setOptionName(ScienceType.TABLET);
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("wait", testPlayer.getNextAction().toString());
		Assertions.assertTrue(r1 instanceof ActionResponse);
		Assertions.assertEquals(1, testPlayer.getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}	

	@Test
	public void when_choosing_science_then_added_to_other_science() {
		setupTest();
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		fakePlayingCard(testPlayer, new Apothecary(3,1), gameWithThreePlayers);
		fakePlayingCard(testPlayer, new Academy(3,3), gameWithThreePlayers);
		fakePlayingCard(testPlayer, new Dispensary(3,2), gameWithThreePlayers);
		fakePlayingCard(testPlayer, new Laboratory(3,2), gameWithThreePlayers);
		fakePlayingCard(testPlayer, new Library(3,2), gameWithThreePlayers);
		
		ChooseScience cs = new ChooseScience();
		testPlayer.addNextAction(cs);
		
		Assertions.assertFalse(gameWithThreePlayers.getFlow().isFinalTurn());
		
		ChooseScienceRequest r = new ChooseScienceRequest();
		r.setOptionName(ScienceType.GEAR);
		BaseResponse r1 = testPlayer.doAction(r, gameWithThreePlayers);
		
		Assertions.assertEquals("wait", testPlayer.getNextAction().toString());
		Assertions.assertTrue(r1 instanceof ActionResponse);
		Assertions.assertEquals(21, testPlayer.getFinalVictoryPoints().get(VictoryPointType.SCIENCE));
	}
}
