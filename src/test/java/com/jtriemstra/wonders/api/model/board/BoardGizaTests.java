package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BoardGizaTests extends BoardTestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		setupTest();
		
		int originalCoins = testPlayer.getCoins();
		
		Board b = new Giza(true);
		WonderStage s = b.build(testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Giza.A1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Giza.A2);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());
		testPlayer.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins, testPlayer.getCoins());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Giza.A3);
		Assertions.assertEquals(3, testPlayer.getVictoryPoints().size());		
	}

	@Test
	public void when_building_side_b_stages_get_correct_values() {
		setupTest();
		
		Board b = new Giza(false);
		WonderStage s = b.build(testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Giza.B1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
				
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Giza.B2);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Giza.B3);
		Assertions.assertEquals(3, testPlayer.getVictoryPoints().size());	
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Giza.B4);
		Assertions.assertEquals(4, testPlayer.getVictoryPoints().size());
	}
}
