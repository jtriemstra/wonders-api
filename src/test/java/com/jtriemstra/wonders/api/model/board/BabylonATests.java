package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.GuardTower;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Babylon-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class BabylonATests extends TestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		Game g = Mockito.spy(setUpGameWithPlayerAndNeighbors());
		Player p = getPresetPlayer(g);
				
		WonderStage s = p.build(g);
		
		Assertions.assertTrue(s instanceof Babylon.A1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		
		s = p.build(g);
		Assertions.assertTrue(s instanceof Babylon.A2);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(g, Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsScience.class));
		
		s = p.build(g);
		Assertions.assertTrue(s instanceof Babylon.A3);
		Assertions.assertEquals(2, p.getVictoryPoints().size());		
	}
	
	@Test
	public void when_starting_get_brick() {
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p = getPresetPlayer(g);
		
		Card c = new GuardTower(3,1);
		assertHasResourcesToPlay(p, c, g);
	}
}
