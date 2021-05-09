package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.action.GetOptionsScience;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.leaders.Maecenas;
import com.jtriemstra.wonders.api.model.card.provider.VictoryPointType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Rome-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
public class RomeATests extends TestBase {
	
	@Test
	public void when_building_side_a_stages_get_correct_values() {
		Game g = Mockito.spy(setUpLeadersGameWithPlayerAndNeighbors());
		Player p = getPresetPlayer(g);
				
		WonderStage s = p.build(g);
		
		Assertions.assertTrue(s instanceof Rome.A1);
		Assertions.assertEquals(4, p.getFinalVictoryPoints().get(VictoryPointType.STAGES));
		
		s = p.build(g);
		Assertions.assertTrue(s instanceof Rome.A2);
		Assertions.assertEquals(10, p.getFinalVictoryPoints().get(VictoryPointType.STAGES));		
	}
	
	@Test
	public void when_starting_can_recruit_leaders_for_free() {
		Game g = setUpLeadersGameWithPlayerAndNeighbors();
		Player p = getPresetPlayer(g);
		
		Card c = new Maecenas();
		assertBankCosts(p, c, g, 0); 
	}
}
