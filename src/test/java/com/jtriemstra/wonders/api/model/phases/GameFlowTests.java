package com.jtriemstra.wonders.api.model.phases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // this is to support repeated calls to the BoardFactory from the injected Game. could also just create a new Game in every test
@Import(TestBase.TestConfig.class)
public class GameFlowTests extends TestBase {
	
	@Autowired
	PlayerFactory playerFactory;
	
	@Autowired
	@Qualifier("gameWithThreePlayers")
	Game gameWithThreePlayers;
	
	@Test
	public void when_starting_game_current_age_increments() {
		testPlayer = gameWithThreePlayers.getPlayer("test1");
		
		assertEquals(0, gameWithThreePlayers.getFlow().getCurrentAge());
		
		gameWithThreePlayers.startNextPhase(); //coins/resources
		gameWithThreePlayers.startNextPhase(); // age 1
		
		assertEquals(1, gameWithThreePlayers.getFlow().getCurrentAge());
	}

	@Test
	public void when_starting_game_player_has_seven_cards() {
		testPlayer = gameWithThreePlayers.getPlayer("test1");
		
		assertEquals(0, testPlayer.getHandSize());
		
		gameWithThreePlayers.startNextPhase(); //coins/resources
		gameWithThreePlayers.startNextPhase(); // age 1
		
		assertEquals(7, testPlayer.getHandSize());
	}
	

	@Test
	public void when_adding_player_get_resource_from_board() {
		testPlayer = gameWithThreePlayers.getPlayer("test1");
		
		gameWithThreePlayers.startNextPhase();
		
		Assertions.assertEquals(1, testPlayer.getResources(true).size());
		Assertions.assertEquals(ResourceType.PAPER, testPlayer.getResources(true).get(0).getSingle());
	}
}
