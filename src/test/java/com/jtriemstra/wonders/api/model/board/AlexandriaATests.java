package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.Workshop;
import com.jtriemstra.wonders.api.model.card.provider.ResourceProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Alexandria-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AlexandriaATests extends TestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p = getPresetPlayer(g);
		
		int originalCoins = p.getCoins();
		
		WonderStage s = p.build(g);
		
		Assertions.assertTrue(s instanceof Alexandria.A1);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		
		s =  p.build(g);
		Assertions.assertTrue(s instanceof Alexandria.A2);
		Assertions.assertEquals(1, p.getVictoryPoints().size());
		p.gainCoinsFromCardOrBoard();
		Mockito.verify(p, Mockito.times(1)).addResourceProvider(Mockito.any(ResourceProvider.class), Mockito.eq(false));
		
		s = p.build(g);
		Assertions.assertTrue(s instanceof Alexandria.A3);
		Assertions.assertEquals(2, p.getVictoryPoints().size());		
	}
	
	@Test
	public void when_starting_get_glass() {
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p = getPresetPlayer(g);
		
		Card c = new Workshop(3,1);
		assertHasResourcesToPlay(p, c, g);
	}
}
