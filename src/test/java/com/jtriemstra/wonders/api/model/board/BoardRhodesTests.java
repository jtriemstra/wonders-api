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
public class BoardRhodesTests extends TestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		Game g = setUpGame();
		Player p = setUpPlayer(g);
		
		int originalCoins = p.getCoins();
		
		Board b = new Rhodes(true);
		WonderStage s = b.build(p, g);
		
		Assertions.assertTrue(s instanceof Rhodes.A1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Rhodes.A2);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(2, p.getArmies());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Rhodes.A3);
		Assertions.assertEquals(2, p.getVictoryPoints().size());		
	}

	@Test
	public void when_building_side_b_stages_get_correct_values() {
		Game g = setUpGame();
		Player p = setUpPlayer(g);
		
		int originalCoins = p.getCoins();
		
		Board b = new Rhodes(false);
		WonderStage s = b.build(p, g);
		
		Assertions.assertTrue(s instanceof Rhodes.B1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins + 3, p.getCoins());
		Assertions.assertEquals(1, p.getArmies());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Rhodes.B2);
		Assertions.assertEquals(2, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins + 7, p.getCoins());
		Assertions.assertEquals(2, p.getArmies());
	}
}
