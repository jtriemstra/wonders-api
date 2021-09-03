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
public class BoardEphesusTests extends TestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		setupTest();
		
		int originalCoins = testPlayer.getCoins();
		
		Board b = new Ephesus(true);
		WonderStage s = b.build(testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Ephesus.A1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Ephesus.A2);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		testPlayer.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins + 9, testPlayer.getCoins());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Ephesus.A3);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());		
	}

	@Test
	public void when_building_side_b_stages_get_correct_values() {
		setupTest();
		
		int originalCoins = testPlayer.getCoins();
		
		Board b = new Ephesus(false);
		WonderStage s = b.build(testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Ephesus.B1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		testPlayer.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins + 4, testPlayer.getCoins());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Ephesus.B2);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());
		testPlayer.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins + 8, testPlayer.getCoins());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Ephesus.B3);
		Assertions.assertEquals(3, testPlayer.getVictoryPoints().size());	
		testPlayer.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins + 12, testPlayer.getCoins());
	}
}
