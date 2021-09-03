package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.Maecenas;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;
import com.jtriemstra.wonders.api.model.deck.leaders.LeaderDeck;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Rome-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RomeATests extends BoardTestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		setupTest();
				
		WonderStage s = testPlayer.build(gameWithThreePlayers);
		
		Assertions.assertTrue(s instanceof Rome.A1);
		Assertions.assertEquals(4, testPlayer.getFinalVictoryPoints().get(VictoryPointType.STAGES));
		
		s = testPlayer.build(gameWithThreePlayers);
		Assertions.assertTrue(s instanceof Rome.A2);
		Assertions.assertEquals(10, testPlayer.getFinalVictoryPoints().get(VictoryPointType.STAGES));		
	}
	
	@Test
	public void when_starting_can_recruit_leaders_for_free() {
		setupTest();
		
		Card c = new Maecenas();
		assertBankCosts(testPlayer, c, gameWithThreePlayers, 0); 
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
		
	}
}
