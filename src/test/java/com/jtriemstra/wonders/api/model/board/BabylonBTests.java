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

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.board.Babylon.GetOptionsBabylon;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.GuardTower;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Babylon-B;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BabylonBTests extends BoardTestBase {
	
	@Test
	public void when_building_side_b_stages_get_correct_values() {
		setupTest();
				
		int originalCoins = testPlayer.getCoins();
		
		WonderStage s = testPlayer.build(gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Babylon.B1);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		Assertions.assertEquals(originalCoins, testPlayer.getCoins());
		
		s = testPlayer.build(gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Babylon.B2);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());
		
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(1)).addPostTurnAction(Mockito.any(Player.class), Mockito.any(GetOptionsBabylon.class), Mockito.any());
		
		s = testPlayer.build(gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Babylon.B3);
		Assertions.assertEquals(1, testPlayer.getVictoryPoints().size());	
		
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(1)).addPostGameAction(Mockito.any(Player.class), Mockito.any(GetOptionsScience.class), Mockito.any());
	}
	
	@Test
	public void when_starting_get_brick() {
		setupTest();
		
		Card c = new GuardTower(3,1);
		assertHasResourcesToPlay(testPlayer, c, gameWithThreePlayers);
	}
	
	@TestConfiguration
	public static class TestConfig {
		
		@Bean
		@Scope("prototype")
		@Primary
		public GameFlowFactory spyGameFlowFactory() {
			return phaseFactory -> {
				GameFlow spyFlow = Mockito.spy(new GameFlow(phaseFactory));
						
				return spyFlow;
			};
		}
	}
}
