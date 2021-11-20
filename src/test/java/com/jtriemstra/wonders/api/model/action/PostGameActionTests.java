package com.jtriemstra.wonders.api.model.action;

import static org.mockito.Mockito.mockitoSession;

import java.util.ArrayList;
import java.util.List;

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

import com.jtriemstra.wonders.api.FinalTurnFinalAgeTestConfiguration;
import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GamePhaseFactoryFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.phases.AgePhase;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.Phase;
import com.jtriemstra.wonders.api.model.phases.StartingResourceAndCoinsPhase;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import({TestBase.TestConfig.class, FinalTurnFinalAgeTestConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostGameActionTests extends TestBase {
	
	@Test
	public void when_finishing_game_choose_science_option_works() {
		setupTest();
		
		//mock all waiting, since startNextPhase messed that up
		gameWithThreePlayers.doForEachPlayer(p -> p.addNextAction(new WaitTurn()));
				
		Player p2 = gameWithThreePlayers.getPlayer("test2");
		Player p3 = gameWithThreePlayers.getPlayer("test3");
				
		gameWithThreePlayers.getFlow().addPostGameAction(testPlayer, new GetOptionsScience(), AgePhase.class);
		
		WaitTurn w = new WaitTurn();
		w.execute(null, testPlayer, gameWithThreePlayers);
		
		Assertions.assertEquals("options", testPlayer.getNextAction().toString());
		Assertions.assertEquals("wait", p2.getNextAction().toString());
		Assertions.assertEquals("wait", p3.getNextAction().toString());
		
		Assertions.assertTrue(testPlayer.getNextAction().getByName("options") instanceof GetOptionsScience);
	}
	
	@TestConfiguration
	public static class TestConfig {
		@Bean
		@Scope("prototype")
		@Primary
		public GamePhaseFactoryFactory createMockPhaseFactory() {
			return (deckFactory, numberOfPlayers, ptaFactory) -> {
				return () -> {
					List<Phase> result = new ArrayList<>(); 
					result.add(new StartingResourceAndCoinsPhase());
					AgePhase spyAge = Mockito.spy(new AgePhase(deckFactory, numberOfPlayers, 3, ptaFactory.getPostTurnActions(), new PostTurnActions()));
					Mockito.when(spyAge.isFinalTurn()).thenReturn(true);
					result.add(spyAge);
					
					return result;
				};
			};
		}
	}
}
