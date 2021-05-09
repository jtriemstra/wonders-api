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
import com.jtriemstra.wonders.api.model.board.Babylon.GetOptionsBabylon;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.GuardTower;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Babylon-B;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class BabylonBTests extends TestBase {
	
	@Test
	public void when_building_side_b_stages_get_correct_values() {
		Game g = Mockito.spy(setUpGameWithPlayerAndNeighbors());
		Player p = getPresetPlayer(g);
		
		int originalCoins = p.getCoins();
		
		WonderStage s = p.build(g);
		
		Assertions.assertTrue(s instanceof Babylon.B1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins, p.getCoins());
		
		s = p.build(g);
		Assertions.assertTrue(s instanceof Babylon.B2);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(g, Mockito.times(1)).addPostTurnAction(Mockito.any(Player.class), Mockito.any(GetOptionsBabylon.class));
		
		s = p.build(g);
		Assertions.assertTrue(s instanceof Babylon.B3);
		Assertions.assertEquals(1, p.getVictoryPoints().size());	
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(g, Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsScience.class));
	}
	
	@Test
	public void when_starting_get_brick() {
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p = getPresetPlayer(g);
		
		Card c = new GuardTower(3,1);
		assertHasResourcesToPlay(p, c, g);
	}
}
