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
import com.jtriemstra.wonders.api.model.card.provider.ResourceProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class BoardAlexandriaTests extends TestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		Game g = setUpGame();
		Player p = setUpPlayer(g);
		
		int originalCoins = p.getCoins();
		
		Board b = new Alexandria(true);
		WonderStage s = b.build(p, g);
		
		Assertions.assertTrue(s instanceof Alexandria.A1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Alexandria.A2);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(p, Mockito.times(1)).addResourceProvider(Mockito.any(ResourceProvider.class), Mockito.eq(false));
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Alexandria.A3);
		Assertions.assertEquals(2, p.getVictoryPoints().size());		
	}

	@Test
	public void when_building_side_b_stages_get_correct_values() {
		Game g = setUpGame();
		Player p = setUpPlayer(g);
		
		int originalCoins = p.getCoins();
		
		Board b = new Alexandria(false);
		WonderStage s = b.build(p, g);
		
		Assertions.assertTrue(s instanceof Alexandria.B1);
		Assertions.assertEquals(0, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(p, Mockito.times(1)).addResourceProvider(Mockito.any(ResourceProvider.class), Mockito.eq(false));
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Alexandria.B2);
		Assertions.assertEquals(0, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(p, Mockito.times(2)).addResourceProvider(Mockito.any(ResourceProvider.class), Mockito.eq(false));
		
		s = b.build(p, g);
		Assertions.assertTrue(s instanceof Alexandria.B3);
		Assertions.assertEquals(1, p.getVictoryPoints().size());	
		p.gainCoinsFromCardOrBoard();
		
	}
}
