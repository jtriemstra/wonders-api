package com.jtriemstra.wonders.api.model.card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@Import(TestBase.TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ArmyCardTests extends TestBase {
		
	@Test
	public void when_playing_stockade_get_one_army() {
		setupTest(new Stockade(3,1));
		
		Assertions.assertEquals(1, testPlayer.getArmyFacade().getArmies());
	}
	
	@Test
	public void when_playing_stockade_and_barracks_get_two_army() {
		setupTest(new Stockade(3,1), new Barracks(3,1));
				
		Assertions.assertEquals(2, testPlayer.getArmyFacade().getArmies());
	}
	
	@Test
	public void when_playing_arsenal_get_three_army() {
		setupTest(new Arsenal(3,1));
				
		Assertions.assertEquals(3, testPlayer.getArmyFacade().getArmies());
	}

}
