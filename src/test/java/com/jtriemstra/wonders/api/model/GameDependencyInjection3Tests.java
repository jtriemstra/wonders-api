package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.model.GeneralBeanFactory.BoardManagerFactory;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.Giza;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.Phases;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameDependencyInjection3Tests {
	@Autowired
	GameFactory gameFactory;
	

	@Autowired
	private BoardManagerFactory boardManagerFactory;
	
	//This was originally in a test with spies, and I don't think I can separate spy and real player lists like I did at first
	@Test
	public void when_using_factory_then_game_dependency_spies_are_ignored() {
		CardFactory guildFactory = new GuildCardFactoryBasic();
		DeckFactory deckFactory = new DefaultDeckFactory(new AgeCardFactory(), guildFactory);
		GamePhaseFactory phaseFactory = new GamePhaseFactoryBasic(deckFactory, 3);
		BoardSource boardSource = new BoardSourceBasic();
		BoardManager boardManager = boardManagerFactory.getManager(boardSource, BoardSide.A_OR_B);
		
		Game g = gameFactory.createGame("test1", 3, new Phases(phaseFactory), boardManager);
		assertEquals(0, g.getNumberOfPlayers());
	}
	
	
}
