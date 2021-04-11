package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.board.Ephesus;
import com.jtriemstra.wonders.api.model.board.WonderStage;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class BoardHaliTests extends TestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		Game g = Mockito.spy(setUpGame());
		Player p = setUpPlayer(g);
		
		int originalCoins = p.getCoins();
		
		Board b = new Halikarnassos(true);
		WonderStage s = b.build(p, g);
		
		Assertions.assertTrue(s instanceof Halikarnassos.A1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Halikarnassos.A2);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins, p.getCoins());
		Mockito.verify(g, Mockito.times(1)).addPostTurnAction(Mockito.any(), Mockito.any());
				
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Halikarnassos.A3);
		Assertions.assertEquals(2, p.getVictoryPoints().size());		
	}

	@Test
	public void when_building_side_b_stages_get_correct_values() {
		Game g = Mockito.spy(setUpGame());
		Player p = setUpPlayer(g);
		
		int originalCoins = p.getCoins();
		
		Board b = new Halikarnassos(false);
		WonderStage s = b.build(p, g);
		
		Assertions.assertTrue(s instanceof Halikarnassos.B1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		Mockito.verify(g, Mockito.times(1)).addPostTurnAction(Mockito.any(), Mockito.any());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Halikarnassos.B2);
		Assertions.assertEquals(2, p.getVictoryPoints().size());
		Mockito.verify(g, Mockito.times(2)).addPostTurnAction(Mockito.any(), Mockito.any());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Halikarnassos.B3);
		Assertions.assertEquals(2, p.getVictoryPoints().size());	
		Mockito.verify(g, Mockito.times(3)).addPostTurnAction(Mockito.any(), Mockito.any());
	}
}
