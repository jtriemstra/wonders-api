package com.jtriemstra.wonders.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;

import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.DiscardPile;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.IPlayer;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.PlayerArmyFacade;
import com.jtriemstra.wonders.api.model.PlayerList;
import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.action.BaseAction;
import com.jtriemstra.wonders.api.model.action.WaitTurn;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSource;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.NamedBoardStrategy;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.CardPlayable;
import com.jtriemstra.wonders.api.model.card.provider.ScienceProvider;
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.leaders.PlayerLeaders;
import com.jtriemstra.wonders.api.model.phases.GameFlow;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.PostTurnActionFactoryDefault;
import com.jtriemstra.wonders.api.model.playbuildrules.PlayableBuildableResult;
import com.jtriemstra.wonders.api.notifications.NotificationService;

public class UnitTestCaseBuilder {
	
	private int numberOfPlayers = 3;
	private DiscardPile discard;
	private PlayerList players;
	
	private CardFactory ageCardFactory;
	private CardFactory guildCardFactory;
	
	private DeckFactory deckFactory;
	private PostTurnActionFactoryDefault ptaFactory;
	private GamePhaseFactory phaseFactory;
	private GameFlow phases;
	
	private BoardSource source;
	private BoardStrategy strategy;
	private BoardSide sides = BoardSide.A_ONLY;
	private BoardManager boards;
	
	private GameFlow mockFlow;

	public static UnitTestCaseBuilder create() {
		return new UnitTestCaseBuilder();
	}
	
	public UnitTestCaseBuilder withPlayerNames(String...names) {
		players = new PlayerList();
		numberOfPlayers = names.length;
		for (String s : names) {
			ActionList a = new ActionList();
			a.push(new WaitTurn());
			Player spyPlayer = Mockito.spy(new Player(s, a, new ArrayList<>(), new ArrayList<>(), new CardList(), Mockito.mock(NotificationService.class)));
			players.addPlayer(spyPlayer);	
		}	
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerCardsInHand(String name, Card...cards) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (IPlayer p : players) {
			if (name.equals(p.getName())) {
				for (Card c : cards) {
					p.receiveCard(c);
				}
			}
		}
		return this;
	}

	public UnitTestCaseBuilder withPlayerCardsOnBoard(String name, Card...cards) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (IPlayer p : players) {
			if (name.equals(p.getName())) {
				for (Card c : cards) {
					p.putCardOnBoard(c);
				}
			}
		}
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerNextAction(String name, BaseAction a) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (IPlayer p : players) {
			if (name.equals(p.getName())) {
				p.addNextAction(a);
			}
		}
		
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerBuiltStages(String name, int stages) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (IPlayer p : players) {
			if (name.equals(p.getName())) {
				Mockito.doReturn(stages).when(p).getNumberOfBuiltStages();
			}
		}
		
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerDefeats(String name, int defeats) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (IPlayer p : players) {
			if (name.equals(p.getName())) {
				PlayerArmyFacade mockArmies = Mockito.mock(PlayerArmyFacade.class);
				Mockito.when(mockArmies.getNumberOfDefeats()).thenReturn(defeats);
				Mockito.when(p.getArmyFacade()).thenReturn(mockArmies);
			}
		}
		
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerScienceProviders(String name, ScienceProvider...providers) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (IPlayer p : players) {
			if (name.equals(p.getName())) {
				Mockito.when(p.getScienceProviders()).thenReturn(Arrays.asList(providers));
			}
		}
		
		return this;
	}
	
	public UnitTestCaseBuilder withInitialBoards(String boards) {
		strategy = new NamedBoardStrategy(boards);
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerPlayableCards(String name, CardPlayable...playables) {
		List<CardPlayable> playableCards = Arrays.asList(playables);
		
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (IPlayer p : players) {
			if (name.equals(p.getName())) {
				Mockito.doReturn(playableCards).when(p).getPlayableCards(Mockito.any(), Mockito.any(), Mockito.any());
			}
		}
		
		return this;
	}
	
	public UnitTestCaseBuilder withFinalAgeAndTurn() {
		mockFlow = Mockito.mock(GameFlow.class);
		Mockito.doReturn(true).when(mockFlow).isFinalAge();
		Mockito.doReturn(true).when(mockFlow).isFinalTurn();
		
		return this;
	}
	
	public UnitTestCaseBuilder withDiscardCards(Card...cards) {
		
		discard = new DiscardPile();
		for (Card c : cards) {
			discard.add(c);
		}
		
		return this;
	}

	public UnitTestCaseBuilder withPlayerCanBuild(String name, PlayableBuildableResult buildable) {
		
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (IPlayer p : players) {
			if (name.equals(p.getName())) {
				Mockito.doReturn(buildable).when(p).canBuild(Mockito.any(), Mockito.any(), Mockito.any());
			}
		}
		
		return this;
	}
	
	public BoardManager getBoardManager() {
		if (strategy == null) strategy = new NamedBoardStrategy("Ephesus-A;Ephesus-A;Ephesus-A");
		if (source == null) source = new BoardSourceBasic();
		if (boards == null) boards = new BoardManager(source, strategy, sides);
		
		return boards;
	}
	
	public IPlayer getPlayer(String s) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		return players.getPlayer(s);
	}
	
	public UnitTestCaseBuilder withLeaders() {
		if (players != null) {
			PlayerList newPlayers = new PlayerList();
			for (IPlayer p : players) {
				newPlayers.addPlayer(Mockito.spy(new PlayerLeaders(p)));
			}
			players = newPlayers;
		}
		
		return this;
	}
	
	public Game build() {
		
		if (discard == null) discard = new DiscardPile();
		if (players == null) withPlayerNames("test1","test2","test3");
		
		if (ageCardFactory == null) ageCardFactory = new AgeCardFactory();
		if (guildCardFactory == null) guildCardFactory = new GuildCardFactoryBasic();
		
		if (deckFactory == null) deckFactory = new DefaultDeckFactory(ageCardFactory, guildCardFactory);
		if (ptaFactory == null) ptaFactory = new PostTurnActionFactoryDefault(discard);
		
		if (phaseFactory == null) phaseFactory = new GamePhaseFactoryBasic(deckFactory, numberOfPlayers, ptaFactory);
		if (phases == null) phases = new GameFlow(phaseFactory);
		
		if (strategy == null) strategy = new NamedBoardStrategy("Ephesus-A;Ephesus-A;Ephesus-A");
		if (source == null) source = new BoardSourceBasic();
		if (boards == null) boards = new BoardManager(source, strategy, sides);		
		
		boolean isSpied = false;
		
		Game g = new Game("test-game", numberOfPlayers, discard, new PlayerList(), phases, boards);
		for (IPlayer p : players) {
			g.addPlayer(p);
		}
		
		if (mockFlow != null) {
			if (!isSpied) {
				g = Mockito.spy(g);
				isSpied = true;
			}
			Mockito.doReturn(mockFlow).when(g).getFlow();
		}
		
		return g;
	}
}
