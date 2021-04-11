package com.jtriemstra.wonders.api;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import com.jtriemstra.wonders.api.dto.request.PlayRequest;
import com.jtriemstra.wonders.api.model.Ages;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.GameFactory;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerFactory;
import com.jtriemstra.wonders.api.model.action.Play;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.board.Babylon;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.board.Ephesus;
import com.jtriemstra.wonders.api.model.board.Rhodes;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.CardPlayable.Status;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacadeLeaders;

public class TestBase {

	@Autowired
	@Qualifier("createGameFactory")
	protected GameFactory gameFactory;
	
	@Autowired
	@Qualifier("createActivePhaseGameFactory")
	protected GameFactory activePhaseGameFactory;
	
	@Autowired
	@Qualifier("createFinalTurnGameFactory")
	protected GameFactory finalTurnGameFactory;
	
	@Autowired
	@Qualifier("createFinalAgeGameFactory")
	protected GameFactory finalAgeGameFactory;
	
	@Autowired
	@Qualifier("createNamedBoardFactory")
	protected BoardFactory boardFactory;

	@Autowired
	@Qualifier("createPlayerFactory")
	protected PlayerFactory playerFactory;

	protected Game setUpGame() {
		return setUpGame(activePhaseGameFactory);
	}
	
	protected Game setUpGame(GameFactory gf) {
		return gf.createGame("test1", boardFactory);
	}

	protected Game setUpFinalTurnGame() {
		return finalTurnGameFactory.createGame("test1", boardFactory);
	}
	
	protected Player setUpPlayer(Game g) {
		Player p = Mockito.spy(playerFactory.createPlayer("test1"));
		g.addPlayer(p);
		
		p.setPointCalculations(new VictoryPointFacadeLeaders());
		
		p.addNextAction(new WaitTurn());
		
		return p;
	}
	
	protected Player setUpPlayerWithCard(Card hand, Game g) {
		Player p = setUpPlayer(g);
		setUpCardToPlay(p, hand);
		
		return p;
	}
	
	//TODO: migrate everything to the Action version?
	protected void setUpCardToPlay(Player p, Card c) {
		p.receiveCard(c);
	}
	
	protected void setUpCardToPlayWithAction(Player p, Card c, Game g) {
		p.receiveCard(c);
		CardPlayable cp = p.canPlay(c, g.getLeftOf(p), g.getRightOf(p));
		List<CardPlayable> cps = new ArrayList<>();
		cps.add(cp);
		
		Play play = new Play(cps);
		p.addNextAction(new WaitTurn());
		p.addNextAction(play);
	}
	
	protected CardPlayable getFakeCardPlayable(Card c) {
		return new CardPlayable(c, Status.OK, 0, 0, 0);
	}
	
	protected void setUpCardToPlayWithActionIgnoreResources(Player p, Card c, Game g) {
		p.receiveCard(c);
		CardPlayable cp = getFakeCardPlayable(c);
		List<CardPlayable> cps = new ArrayList<>();
		cps.add(cp);
		
		Play play = new Play(cps);
		p.addNextAction(new WaitTurn());
		p.addNextAction(play);
	}
	
	protected void setUpNeighbors(Game g, Player p) {
		Player p2 = Mockito.spy(playerFactory.createPlayer("test2"));
		Player p3 = Mockito.spy(playerFactory.createPlayer("test3"));
		
		g.addPlayer(p2);
		g.addPlayer(p3);
	}
	
	protected void fakePreviousCard(Player p1, Card c, Game g) {
		p1.receiveCard(c);
		p1.scheduleCardToPlay(c);
		p1.playCard(g);
	}
	
	protected void fakeVictoryTokens(Player p, int age) {
		int amount = 2*age - 1;
		p.addVictory(age, amount);
	}

	protected void fakeDefeatTokens(Player p, int age) {
		p.addDefeat(age);
	}
	
	protected void fakeBuildingStage(Player p, Game g) {
		p.build(g);
	}
	
	protected void replicatePlayingCard(Player p1, Card c, Game g) {
		p1.scheduleCardToPlay(c);
		p1.playCard(g);		
	}

	protected void replicatePlayingCardWithAction(Player p1, Card c, Game g) {
		PlayRequest r = new PlayRequest();
		r.setCardName(c.getName());
		
		p1.doAction(r, g);
	}
	
	protected void fakeFinishingTurn(Game g) {
		g.handlePostTurnActions();
	}

	protected static void setUpDefaultPostTurnActions(PostTurnActions postTurnActions, Game g) {
		postTurnActions.add(null, g.new PlayCardsAction());
		postTurnActions.add(null, g.new ResolveCommerceAction());
		postTurnActions.add(null, g.new DiscardFinalCardAction());
		postTurnActions.add(null, g.new ResolveConflictAction());
	}
	
	@TestConfiguration
	public static class TestConfig {

		@Autowired
		@Qualifier("createGameFactory")
		GameFactory gameFactory;
		
		@Bean
		@Profile("test")
		@Primary //TODO: this is a bit of a dummy thing to make MainController happy. Probably a better way to handle that.
		public GameFactory createActivePhaseGameFactory() {
			return (name, boardFactory) -> createActivePhaseGame(name, boardFactory);
		}
		
		public Game createActivePhaseGame(String gameName, BoardFactory boardFactory) {
			Game g = gameFactory.createGame(gameName, boardFactory);
			g.startNextPhase();
			
			return g;
		}
		
		@Bean
		@Profile("test")
		public GameFactory createFinalTurnGameFactory() {
			return (name, boardFactory) -> createFinalTurnGame(name, boardFactory);
		}
		
		@Bean
		@Scope("prototype")
		public Game createFinalTurnGame(String gameName, BoardFactory boardFactory) {
			Ages spyAges = Mockito.spy(new Ages());
			Mockito.doReturn(true).when(spyAges).isFinalTurn();
			Mockito.doReturn(1).when(spyAges).getCurrentAge();
			
			PostTurnActions postTurnActions = new PostTurnActions();
			
			Game g = new Game(gameName, boardFactory, spyAges, new DefaultDeckFactory(new AgeCardFactory(), new GuildCardFactoryBasic()), postTurnActions, new PostTurnActions());
			
			g = Mockito.spy(g);
			Mockito.when(g.isPhaseStarted()).thenReturn(true);
			
			setUpDefaultPostTurnActions(postTurnActions, g);
			
			return g;
		}
		
		@Bean
		@Profile("test")
		public GameFactory createFinalAgeGameFactory() {
			return (name, boardFactory) -> createFinalAgeGame(name, boardFactory);
		}
		
		@Bean
		@Scope("prototype")
		public Game createFinalAgeGame(String gameName, BoardFactory boardFactory) {
			Ages spyAges = Mockito.spy(new Ages());
			Mockito.doReturn(true).when(spyAges).isFinalAge();
			Mockito.doReturn(true).when(spyAges).isFinalTurn();
			Game g = new Game(gameName, boardFactory, spyAges, new DefaultDeckFactory(new AgeCardFactory(), new GuildCardFactoryBasic()), new PostTurnActions(), new PostTurnActions());
			
			return g;
		}
	}
}
