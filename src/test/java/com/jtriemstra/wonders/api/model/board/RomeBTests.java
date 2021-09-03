package com.jtriemstra.wonders.api.model.board;

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
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.GameFlowFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsRecruitLeaderRome;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.Alexander;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;
import com.jtriemstra.wonders.api.model.phases.GameFlow;
import com.jtriemstra.wonders.api.model.phases.PhaseMatcher;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Rome-B;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RomeBTests extends BoardTestBase {
	
	@Test
	public void when_building_side_b_stages_get_correct_values() {
		setupTest();
		
		int originalCoins = testPlayer.getCoins();
		int originalLeaderCards = testPlayer.getNumberOfLeaderCards();
				
		WonderStage s = testPlayer.build(gameWithThreePlayers);
		
		testPlayer.gainCoinsFromCardOrBoard();
		Assertions.assertTrue(s instanceof Rome.B1);
		Assertions.assertEquals(originalCoins + 5, testPlayer.getCoins());
		Assertions.assertEquals(originalLeaderCards + 4, testPlayer.getNumberOfLeaderCards());
				
		s = testPlayer.build(gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Rome.B2);
		Assertions.assertEquals(3, testPlayer.getFinalVictoryPoints().get(VictoryPointType.STAGES));
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(1)).addPostTurnAction(Mockito.any(Player.class), Mockito.any(GetOptionsRecruitLeaderRome.class), Mockito.any(PhaseMatcher.class));

		s = testPlayer.build(gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Rome.B3);
		Assertions.assertEquals(6, testPlayer.getFinalVictoryPoints().get(VictoryPointType.STAGES));
		Mockito.verify(gameWithThreePlayers.getFlow(), Mockito.times(2)).addPostTurnAction(Mockito.any(Player.class), Mockito.any(GetOptionsRecruitLeaderRome.class), Mockito.any(PhaseMatcher.class));
	}
	
	@Test
	public void when_starting_can_recruit_leaders_for_discount() {
		setupTest();
		
		Player p2 = gameWithThreePlayers.getPlayer("test2");
		Player p3 = gameWithThreePlayers.getPlayer("test3");
		
		Card c = new Alexander();
		assertBankCosts(testPlayer, c, gameWithThreePlayers, 1);
		assertBankCosts(p2, c, gameWithThreePlayers, 2);
		assertBankCosts(p3, c, gameWithThreePlayers, 2);
	}

	@TestConfiguration
	public static class TestConfig {

		@Autowired
		LeaderDeck leaderDeck;		

		@Bean
		@Scope("prototype")
		@Primary
		public BoardSource leaderBoardSource(@Qualifier("boardSource") BoardSource input) {
			return new BoardSourceLeadersDecorator(input, leaderDeck);
		} 
		
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
