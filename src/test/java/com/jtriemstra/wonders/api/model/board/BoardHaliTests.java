package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.SpyGameFlowTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, SpyGameFlowTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BoardHaliTests extends BoardTestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		setupTest();
		
		int originalCoins = testPlayer.getCoins();
		
		Board b = new Halikarnassos(true);
		WonderStage s = b.build(testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Halikarnassos.A1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Halikarnassos.A2);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		Assertions.assertEquals(originalCoins, testPlayer.getCoins());
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(1)).addPostTurnAction(Mockito.any(), Mockito.any(), Mockito.any());
				
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Halikarnassos.A3);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());		
	}

	@Test
	public void when_building_side_b_stages_get_correct_values() {
		setupTest();
		
		Board b = new Halikarnassos(false);
		WonderStage s = b.build(testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Halikarnassos.B1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(1)).addPostTurnAction(Mockito.any(), Mockito.any(), Mockito.any());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Halikarnassos.B2);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(2)).addPostTurnAction(Mockito.any(), Mockito.any(), Mockito.any());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Halikarnassos.B3);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());	
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(3)).addPostTurnAction(Mockito.any(), Mockito.any(), Mockito.any());
	}
	
}
