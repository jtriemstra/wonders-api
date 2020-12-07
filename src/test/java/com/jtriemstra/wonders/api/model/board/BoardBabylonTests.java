package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.board.Babylon.GetOptionsBabylon;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class BoardBabylonTests {

	@Autowired
	PlayerFactory playerFactory;
	
	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		//TODO: refactor so current state of stages can be mocked separately
		Game g = Mockito.spy(gameFactory.createGame("test1", boardFactory));
		Player p = playerFactory.createPlayer("test1");
				
		Board b = new Babylon(true);
		WonderStage s = b.build(p, g);
		
		Assertions.assertTrue(s instanceof Babylon.A1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Babylon.A2);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(g, Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsScience.class));
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Babylon.A3);
		Assertions.assertEquals(2, p.getVictoryPoints().size());		
	}

	@Test
	public void when_building_side_b_stages_get_correct_values() {
		//TODO: refactor so current state of stages can be mocked separately
		Game g = Mockito.spy(gameFactory.createGame("test1", boardFactory));
		Player p = playerFactory.createPlayer("test1");
		
		int originalCoins = p.getCoins();
		
		Board b = new Babylon(false);
		WonderStage s = b.build(p, g);
		
		Assertions.assertTrue(s instanceof Babylon.B1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Assertions.assertEquals(originalCoins, p.getCoins());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Babylon.B2);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(g, Mockito.times(1)).addPostTurnAction(Mockito.any(Player.class), Mockito.any(GetOptionsBabylon.class));
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Babylon.B3);
		Assertions.assertEquals(1, p.getVictoryPoints().size());	
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(g, Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsScience.class));
	}
}
