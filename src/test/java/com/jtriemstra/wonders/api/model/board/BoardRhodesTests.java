package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

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
public class BoardRhodesTests {

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
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p = playerFactory.createPlayer("test1");
		
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
		//TODO: refactor so current state of stages can be mocked separately
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p = playerFactory.createPlayer("test1");
		
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
