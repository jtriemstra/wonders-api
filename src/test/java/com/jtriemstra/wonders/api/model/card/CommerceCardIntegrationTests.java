package com.jtriemstra.wonders.api.model.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommerceCardIntegrationTests extends TestBase {
	
	@Test
	public void when_playing_arena_integration_test() {
		Card c = new Arena(3,3);
		Game g = setUpGame();
		Player p1 = setUpPlayer(g);
		
		Mockito.doReturn(3).when(p1).getNumberOfBuiltStages();
		
		int originalCoins = p1.getCoins();
		
		setUpCardToPlayWithActionIgnoreResources(p1, c, g);
		
		replicatePlayingCardWithAction(p1, c, g);
		
		fakeFinishingTurn(g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof StageVPProvider);
		Assertions.assertEquals(originalCoins + 9, p1.getCoins());
	}
}
