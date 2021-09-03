package com.jtriemstra.wonders.api.model.card;

import static com.jtriemstra.wonders.api.model.card.LeaderCardTests.DEFAULT_COINS;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.action.ResolveConflictAction;
import com.jtriemstra.wonders.api.model.card.leaders.Nero;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Babylon-A;Rhodes-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LeaderCardTestsForFinalTurn extends TestBase {
	
	@Test
	public void when_playing_nero_get_two_coins_per_victory() {
		setupTest(new GuardTower(3,1), new Nero());
				
		NonPlayerAction a = new ResolveConflictAction();
		a.execute(gameWithThreePlayers);
		
		Assertions.assertEquals(DEFAULT_COINS + 2 + 2, testPlayer.getCoins());
	}
	
	@TestConfiguration
	public static class TestConfig {
		
		@Bean
		@Scope("prototype")
		@Primary
		public GameFlowFactory spyGameFlowFactory() {
			return phaseFactory -> {
				GameFlow spyFlow = Mockito.spy(new GameFlow(phaseFactory));
				Mockito.doReturn(true).when(spyFlow).isFinalTurn();
				Mockito.doReturn(1).when(spyFlow).getCurrentAge();
				Mockito.when(spyFlow.isPhaseStarted()).thenReturn(true);		
				return spyFlow;
			};
		}
	}
}
