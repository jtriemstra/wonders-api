package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.FinalTurnFinalAgeTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.dto.request.OptionsRequest;
import com.jtriemstra.wonders.api.dto.response.BaseResponse;
import com.jtriemstra.wonders.api.dto.response.OptionsGuildResponse;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.ScientistsGuild;
import com.jtriemstra.wonders.api.model.card.SpiesGuild;
import com.jtriemstra.wonders.api.model.card.TradersGuild;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, FinalTurnFinalAgeTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GetOptionsGuildCardFinalAgeTurnTests extends TestBase {
	
	@Test
	public void when_neighbors_have_guild_cards_then_have_choice() {
		setupTest();

		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
		
		fakePlayingCard(gameWithThreePlayers.getPlayer("test2"), new ScientistsGuild(3,3), gameWithThreePlayers);
		fakePlayingCard(gameWithThreePlayers.getPlayer("test2"), new SpiesGuild(3,3), gameWithThreePlayers);
		fakePlayingCard(gameWithThreePlayers.getPlayer("test3"), new TradersGuild(3,3), gameWithThreePlayers);
		
		testPlayer.addNextAction(new GetOptionsGuildCard());
				
		OptionsRequest r = new OptionsRequest();
		
		BaseResponse r1 = Player.doAction(r, testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(r1 instanceof OptionsGuildResponse);
		Assertions.assertEquals("chooseGuild", testPlayer.getNextAction().toString());
	}
}
