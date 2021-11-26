package com.jtriemstra.wonders.api;

import java.util.ArrayList;

import org.mockito.Mockito;

import com.jtriemstra.wonders.api.model.CardList;
import com.jtriemstra.wonders.api.model.DiscardPile;
import com.jtriemstra.wonders.api.model.Game;
import com.jtriemstra.wonders.api.model.Player;
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
import com.jtriemstra.wonders.api.model.deck.AgeCardFactory;
import com.jtriemstra.wonders.api.model.deck.CardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.deck.DefaultDeckFactory;
import com.jtriemstra.wonders.api.model.deck.GuildCardFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GameFlow;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactory;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.PostTurnActionFactoryDefault;

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

	public static UnitTestCaseBuilder create() {
		return new UnitTestCaseBuilder();
	}
	
	public UnitTestCaseBuilder withPlayerNames(String...names) {
		players = new PlayerList();
		numberOfPlayers = names.length;
		for (String s : names) {
			ActionList a = new ActionList();
			a.push(new WaitTurn());
			Player spyPlayer = Mockito.spy(new Player(s, a, new ArrayList<>(), new ArrayList<>(), new CardList(), null));
			players.addPlayer(spyPlayer);	
		}	
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerCardsInHand(String name, Card...cards) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (Player p : players) {
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
		
		for (Player p : players) {
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
		
		for (Player p : players) {
			if (name.equals(p.getName())) {
				p.addNextAction(a);
			}
		}
		
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerBuiltStages(String name, int stages) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (Player p : players) {
			if (name.equals(p.getName())) {
				Mockito.doReturn(stages).when(p).getNumberOfBuiltStages();
			}
		}
		
		return this;
	}
	
	public UnitTestCaseBuilder withPlayerDefeats(String name, int defeats) {
		if (players == null) withPlayerNames("test1","test2","test3");
		
		for (Player p : players) {
			if (name.equals(p.getName())) {
				Mockito.when(p.getNumberOfDefeats()).thenReturn(defeats);
			}
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
		
		Game g = new Game("test-game", numberOfPlayers, discard, new PlayerList(), phases, boards);
		for (Player p : players) {
			g.addPlayer(p);
		}
		
		return g;
	}
}
