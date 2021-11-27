package com.jtriemstra.wonders.api.model.board;

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
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.Workshop;
import com.jtriemstra.wonders.api.model.card.provider.ResourceProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Alexandria-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlexandriaATests extends BoardTestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		setupTest();
		
		WonderStage s = testPlayer.build(gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Alexandria.A1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		s =  testPlayer.build(gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Alexandria.A2);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		Mockito.verify(testPlayer, Mockito.times(1)).addResourceProvider(Mockito.any(ResourceProvider.class), Mockito.eq(false));
		
		s = testPlayer.build(gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Alexandria.A3);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());		
	}
	
	@Test
	public void when_starting_get_glass() {
		Card testCard = new Workshop(3,1); 
		setupTest();
		assertHasResourcesToPlay(testPlayer, testCard, gameWithThreePlayers);
	}
}
