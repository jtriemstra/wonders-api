package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.SpyGameFlowTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsGuildCard;
import com.jtriemstra.wonders.api.model.action.provider.OlympiaOptionsProvider;
import com.jtriemstra.wonders.api.model.card.provider.NaturalTradingProvider;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, SpyGameFlowTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BoardOlympusTests extends BoardTestBase {
	//TODO: refactor so current state of stages can be mocked separately
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		setupTest();
		
		Board b = new Olympia(true);
		WonderStage s = b.build(testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Olympia.A1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Olympia.A2);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		Mockito.verify(testPlayer, Mockito.times(1)).setOptionsFactory(Mockito.any(OlympiaOptionsProvider.class));
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Olympia.A3);
		Assertions.assertEquals(2, testPlayer.getVictoryPoints().size());		
	}

	@Test
	public void when_building_side_b_stages_get_correct_values() {
		setupTest();
		
		Board b = new Olympia(false);
		WonderStage s = b.build(testPlayer, gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Olympia.B1);
		Assertions.assertEquals(0, testPlayer.getVictoryPoints().size());
		
		Mockito.verify(testPlayer, Mockito.times(1)).addTradingProvider(Mockito.any(NaturalTradingProvider.class));
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Olympia.B2);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		
		s = b.build(testPlayer, gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Olympia.B3);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());	
		
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsGuildCard.class), Mockito.any(Class.class));
	}
}
