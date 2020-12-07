package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.PossibleActions;
import com.jtriemstra.wonders.api.model.board.Ephesus;
import com.jtriemstra.wonders.api.model.card.provider.ResourceProvider;
import com.jtriemstra.wonders.api.model.resource.ResourceSet;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class PlayerTests {
	@Autowired
	@Qualifier("createPlayerFactory")
	PlayerFactory playerFactory;
	
	@Test
	public void when_starting_turn_next_action_is_options() {
		Player p = playerFactory.createPlayer("test1");
		p.startTurn();
		
		PossibleActions action = p.getNextAction();
		Assertions.assertEquals("options", action.toString());
	}
	
	@Autowired
	@Qualifier("createResourcePlayerFactory")
	PlayerFactory playerWithResourceFactory;
	
	@Test
	public void when_calling_get_resources_board_and_cards_are_included() {
		Player playerWithResource = playerWithResourceFactory.createPlayer("test1");
		List<ResourceSet> resources = playerWithResource.getResources(true);
		Assertions.assertNotNull(resources);
		Assertions.assertEquals(3, resources.size());
	}
	
	@TestConfiguration
	static class TestConfig {
		
		@Bean
		@Scope("prototype")
		PlayerFactory createResourcePlayerFactory() {
			List<ResourceProvider> publicResources = new ArrayList<>();
			publicResources.add(() -> new ResourceSet(ResourceType.BRICK, ResourceType.ORE));
			publicResources.add(() -> new ResourceSet(ResourceType.WOOD));
			return name -> createPlayerWithResource(name, publicResources);
		}
		
		@Bean
		@Scope("prototype")
		public Player createPlayerWithResource(String playerName, List<ResourceProvider> publicResources) {
			Player p = new Player(playerName, new ActionList(), publicResources, new ArrayList<>(), new CardList());
			p.setBoard(new Ephesus(true));
			return p;
		}
	}
}
