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
import com.jtriemstra.wonders.api.model.action.GetOptionsRecruitLeaderRome;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.Alexander;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Rome-B;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RomeBTests extends TestBase {
	
	@Test
	public void when_building_side_b_stages_get_correct_values() {
		Game g = Mockito.spy(setUpLeadersGameWithPlayerAndNeighbors());
		Player p = getPresetPlayer(g);
		
		int originalCoins = p.getCoins();
		int originalLeaderCards = p.getNumberOfLeaderCards();
				
		WonderStage s = p.build(g);
		
		p.gainCoinsFromCardOrBoard();
		Assertions.assertTrue(s instanceof Rome.B1);
		Assertions.assertEquals(originalCoins + 5, p.getCoins());
		Assertions.assertEquals(originalLeaderCards + 4, p.getNumberOfLeaderCards());
				
		s = p.build(g);
		Assertions.assertTrue(s instanceof Rome.B2);
		Assertions.assertEquals(3, p.getFinalVictoryPoints().get(VictoryPointType.STAGES));
		Mockito.verify(g, Mockito.times(1)).addPostTurnAction(Mockito.any(Player.class), Mockito.any(GetOptionsRecruitLeaderRome.class));

		s = p.build(g);
		Assertions.assertTrue(s instanceof Rome.B3);
		Assertions.assertEquals(6, p.getFinalVictoryPoints().get(VictoryPointType.STAGES));
		Mockito.verify(g, Mockito.times(2)).addPostTurnAction(Mockito.any(Player.class), Mockito.any(GetOptionsRecruitLeaderRome.class));
	}
	
	@Test
	public void when_starting_can_recruit_leaders_for_discount() {
		Game g = setUpLeadersGameWithPlayerAndNeighbors();
		Player p = getPresetPlayer(g);
		Player p2 = g.getPlayer("test2");
		Player p3 = g.getPlayer("test3");
		
		Card c = new Alexander();
		assertBankCosts(p, c, g, 1);
		assertBankCosts(p2, c, g, 2);
		assertBankCosts(p3, c, g, 2);
	}
}
