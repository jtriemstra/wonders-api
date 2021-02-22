package com.jtriemstra.wonders.api.model.card;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.BaseAction;
import com.jtriemstra.wonders.api.model.action.Play;
import com.jtriemstra.wonders.api.model.action.Wait;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.card.provider.StageVPProvider;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {"boardNames=Ephesus-A;Ephesus-A;Ephesus-A"})
public class CommerceCardIntegrationTests {

	@Autowired
	@Qualifier("createPlayActionPlayerFactory")
	PlayerFactory playerWithActionsFactory;
	
	@Autowired
	GameFactory gameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	BoardFactory boardFactory;
	
	@Test
	public void when_playing_arena_integration_test() {
		
		Game g = gameFactory.createGame("test1", boardFactory);
		Player p1 = Mockito.spy(playerWithActionsFactory.createPlayer("test1"));
		g.addPlayer(p1);
		
		Mockito.doReturn(3).when(p1).getNumberOfBuiltStages();
		
		int originalCoins = p1.getCoins();
		
		PlayRequest pr = new PlayRequest();
		pr.setCardName("Arena");
		
		p1.doAction(pr, g);
		WaitTurn w = new WaitTurn();
		w.execute(null, p1, g);
		
		Assertions.assertEquals(1, p1.getVictoryPoints().size());
		Assertions.assertTrue(p1.getVictoryPoints().get(0) instanceof StageVPProvider);
		Assertions.assertEquals(originalCoins + 9, p1.getCoins());
	}
	
	@TestConfiguration
	static class TestConfig {
		
		@Bean
		@Scope("prototype")
		PlayerFactory createPlayActionPlayerFactory() {
			ActionList actions = Mockito.mock(ActionList.class);
			Card c = new Arena(3,3);
			CardPlayable cp = new CardPlayable(c, Status.OK, 0, 0, 0);
			List<CardPlayable> cards = new ArrayList<>();
			cards.add(cp);
			BaseAction a = new Play(cards);
			Mockito.doReturn(a).when(actions).getCurrentByName("play");
			
			return name -> createPlayerWithCardAndActions(name, actions, c);
		}
		
		@Bean
		@Scope("prototype")
		public Player createPlayerWithCardAndActions(String playerName, ActionList actions, Card c) {
			Player p = new Player(playerName, actions, new ArrayList<>(), new ArrayList<>(), new CardList());
			p.receiveCard(c);
			return p;
		}
		
		@Autowired
		PlayerList realPlayerList;
		
		@Bean
		@Primary
		@Scope("prototype")
		PlayerList createMockPlayerList() {
			PlayerList mock = Mockito.spy(realPlayerList);
			
			Mockito.doReturn(true).when(mock).allWaiting();
			
			return mock;
		}
	}
}
