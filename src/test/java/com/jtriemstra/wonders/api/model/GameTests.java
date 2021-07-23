package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.TestBase;
import com.jtriemstra.wonders.api.model.GeneralBeanFactory.BoardManagerFactory;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.Phases;
import com.jtriemstra.wonders.api.model.resource.ResourceType;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // this is to support repeated calls to the BoardFactory from the injected Game. could also just create a new Game in every test
@Import(TestBase.TestConfig.class)
public class GameTests extends TestBase {
	
	@Autowired
	PlayerFactory playerFactory;
	
	@Autowired
	@Qualifier("testGame")
	Game game;

	@Autowired
	private BoardManagerFactory boardManagerFactory;
	
	@Test
	public void can_create_game() {
		CardFactory guildFactory = new GuildCardFactoryBasic();
		DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
		GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
		BoardSource boardSource = new BoardSourceBasic();
		BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
		
		Game g = gameFactory.createGame("test-game", 3, new Phases(phaseFactory), boardManager);
		assertNotNull(g);
		assertEquals("test-game", g.getName());
	}
	
	@Test
	public void can_add_players() {
		Player p1 = Mockito.mock(Player.class);
		Player p2 = Mockito.mock(Player.class);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		
		assertEquals(2, game.getNumberOfPlayers());
	}
	
	@Test
	public void when_adding_players_board_is_not_null() {
		Player p1 = playerFactory.createPlayer("test1");
		Player p2 = playerFactory.createPlayer("test2");
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		
		Assertions.assertNotNull(p1.getBoardName());
		Assertions.assertNotEquals("", p1.getBoardName());
	}
			
	@Test
	public void when_starting_game_current_age_increments() {
		Player p1 = playerFactory.createPlayer("test1");
		Player p2 = Mockito.mock(Player.class);
		Player p3 = Mockito.mock(Player.class);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		
		assertEquals(0, game.getCurrentAge());
		
		game.startAge();
		
		assertEquals(1, game.getCurrentAge());
	}

	//TODO: move this test to a new structure - game.startAge() doesn't handle this any more
	/*@Test
	public void when_starting_game_player_has_seven_cards() {
		Player p1 = playerFactory.createPlayer("test1");
		Player p2 = Mockito.mock(Player.class);
		Player p3 = Mockito.mock(Player.class);
		
		game.addPlayer(p1);
		game.addPlayer(p2);
		game.addPlayer(p3);
		
		assertEquals(0, p1.getHandSize());
		
		game.startAge();
		
		assertEquals(7, p1.getHandSize());
	}*/
	
	@Test
	public void when_adding_player_can_get_by_name() {
		Player p1 = playerFactory.createPlayer("test-new-player");
		game.addPlayer(p1);
		
		Player p1b = game.getPlayer("test-new-player");
		
		Assertions.assertNotNull(p1b);
	}
	
	@Test
	public void when_adding_player_get_resource_from_board() {
		Game g = setUpGameWithPlayerAndNeighbors();
		Player p1 = getPresetPlayer(g);
		
		Assertions.assertEquals(1, p1.getResources(true).size());
		Assertions.assertEquals(ResourceType.PAPER, p1.getResources(true).get(0).getSingle());
	}
	
	
	@TestConfiguration
	static class TestConfig {

		@Autowired 
		DiscardPile discard;

		@Autowired 
		PlayerList players;
		
		@Autowired
		@Qualifier("createNamedBoardStrategy")
		private BoardStrategy boardStrategy;

		@Autowired
		private BoardManagerFactory boardManagerFactory;
		
		@Bean
		@Scope("prototype")
		Game testGame() {
			CardFactory guildFactory = new GuildCardFactoryBasic();
			DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
			GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
			BoardSource boardSource = new BoardSourceBasic();
			BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
			
			return new Game("test", 3, new Ages(), new PostTurnActions(), new PostTurnActions(), discard, players, new Phases(phaseFactory), boardManager);
		}
		
		
	}
}
