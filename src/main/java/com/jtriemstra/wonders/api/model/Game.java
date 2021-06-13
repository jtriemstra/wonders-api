package com.jtriemstra.wonders.api.model;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.board.BoardSide;
import com.jtriemstra.wonders.api.model.board.BoardSourceBasic;
import com.jtriemstra.wonders.api.model.board.BoardStrategy;
import com.jtriemstra.wonders.api.model.board.RandomBoardStrategy;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.deck.AgeDeck;
import com.jtriemstra.wonders.api.model.deck.Deck;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.exceptions.BoardInUseException;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.Phases;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacade;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Game {
	@Getter
	private String name;
	@Getter @Setter
	private int numberOfPlayersExpected=3;
	
	private Ages ages;
	private AtomicBoolean ageIsStarted = new AtomicBoolean(false);
	@Setter
	private DeckFactory deckFactory;
	private PostTurnActions postTurnActions;
	private PostTurnActions postGameActions;
	
	@Getter @Setter
	private boolean isReady;

	//TODO: another thing that maybe should be created before the Game object, instead of setting in UpdateGame. defaulting here to make tests easier
	@Getter @Setter
	private Phases phases = new Phases(new GamePhaseFactoryBasic());
	
	@Autowired
	private DiscardPile discard;
	
	@Autowired
	private PlayerList players;
	
	@Getter @Setter
	private Player creator;
	
	@Getter @Setter
	private InitialCoinStrategy initialCoins = () -> 3;	
	
	@Getter @Setter
	private PointCalculationStrategy defaultCalculation = () -> new VictoryPointFacade();
	
	//TODO: this is only used by Leaders right now, for Rome board. Is there a better place for it? Don't really want to make a connection between boards and phases.
	@Getter @Setter
	private Deck unusedLeaders;
	
	@Getter @Setter
	private BoardManager boardManager;
	
	//TODO: hopefully can pull this out when I re-arrange dependencies - it's really BoardManager that needs this
	@Getter
	private BoardStrategy boardStrategy;
	
	public Game(String name, 
			BoardStrategy boardStrategy,
			Ages ages, 
			DeckFactory deckFactory,
			PostTurnActions postTurnActions,
			PostTurnActions postGameActions) {
		this.name = name;
		this.ages = ages;
		this.deckFactory = deckFactory;
		this.postTurnActions = postTurnActions;
		this.postGameActions = postGameActions;
		this.boardStrategy = boardStrategy;
		//TODO: remove this default
		this.boardManager = new BoardManager(new BoardSourceBasic(), boardStrategy, BoardSide.A_OR_B);
	}
	
	@PostConstruct
	public void postConstruct() {
		postTurnActions.setGame(this);
		postGameActions.setGame(this);
	}

	public int getCurrentAge() {
		return ages.getCurrentAge();
	}
	
	public int getNumberOfPlayers() {
		return players.size();
	}
		
	public Player getLeftOf(Player player) {
		return players.getLeftOf(player);
	}

	public Player getRightOf(Player player) {
		return players.getRightOf(player);
	}
	
	public Player getPlayer(String name) {
		return players.getPlayer(name);
	}
	
	public void discard(Card c) {
		discard.add(c);
	}

	public void addPostTurnAction(Player p, PostTurnAction action) {
		postTurnActions.add(p, action);
	}
	
	public void injectPostTurnAction(Player p, PostTurnAction action, int additionalIndex) {
		//TODO: (low) the only reason to pass the Player here is because the removal requires it
		postTurnActions.inject(p, action, additionalIndex);
	}

	public void addPostGameAction(Player p, PostTurnAction action) {
		postGameActions.add(p, action);
	}

	public boolean isFinalTurn() {
		return ages.isFinalTurn();
	}
	
	public boolean isFinalAge() {
		return ages.isFinalAge();
	}

	public void removePostTurnAction(Player player, Class clazz) {
		postTurnActions.markForRemoval(player, clazz);
	}

	public Card removeFromDiscard(String cardName) {
		return discard.remove(cardName);
	}

	public Card[] getDiscardCards() {
		return discard.getCards();
	}
	
	//TODO: do not like this mutating of Player in this method
	public void addPlayer(Player p) {
		players.addPlayer(p);
		Board b = boardManager.getBoard();
		p.setBoard(b);		
	}
			
	//TODO: this could probably move into AgePhase - and some of them into ChooseLeaderPhase
	public void handlePostTurnActions() {
		log.info("handlePostTurnActions");
		if (!isPhaseStarted()) {
			return;
		}
		//TODO: (low) the post game actions could be done simultaneously, rather than sequentially
		if (postTurnActions.hasNext()) {
			log.info("doing next postTurnAction");
			postTurnActions.doNext();
		}
		else {
			if (ages.isFinalAge() && ages.isFinalTurn() && postGameActions.hasNext()) {
				postGameActions.doNext();						
			}
		}		
	}
	
	public boolean isPhaseStarted() {
		return phases.isPhaseStarted();
	}
	
	public void cleanUpPostTurn() {
		postTurnActions.cleanUp();
	}
	
	public void incrementTurn() {
		ages.finishTurnAndCheckEndOfAge();
	}
	
	public void passCards() {
		if (ages.passClockwise()) {
			players.passCardsClockwise();
		}
		else {
			players.passCardsCounterClockwise();
		}
	}
	
	public void startNextPhase() {
		phases.nextPhase();
		phases.phaseStart(this);
	}
	
	public void phaseLoop() {
		phases.phaseLoop(this);
	}
	
	public void phaseEnd() {
		phases.phaseEnd(this);
	}
	
	public void startAge() {
		log.info("maybe starting age ");
		if (!ageIsStarted.getAndSet(true)) {
			log.info("starting age ");
			ages.incrementAge();	
			dealCards(ages.getCurrentAge());
		}			
	}	
	
	public void endAge() {
		ageIsStarted.set(false);
	}
	
	private void dealCards(int age) {
		AgeDeck deck = deckFactory.getDeck(players.size(), age);
		
		//TODO: parameterize the 7 somehow, Cities expansion will be 8
		for (int i=0; i<7; i++) {
			for (Player p : players) {
				p.receiveCard(deck.draw());
			}			
		}
	}
	
	//TODO: these only apply to the board phase, can they be moved out?
	public Map<String, Boolean> getBoardsInUse() {
		return boardManager.getBoardsInUse();
	}
	
	public Board boardSwap(String oldName, String newName, boolean sideA) {
		try {
			Board b = boardManager.swap(oldName, newName, sideA);
			return b;
		}
		catch (BoardInUseException e) {
			throw new RuntimeException("board already in use");
		}
	}
	
	public void doForEachPlayer(PlayerLoop action) {
		for (Player p : players) {
			action.execute(p);
		}
	}
	
	public interface PlayerLoop {
		public void execute(Player p);
	}
	

	public class PlayCardsAction implements NonPlayerAction, PostTurnAction {
		
		private Player singlePlayerToExecute;
		private double order;

		public PlayCardsAction() {
			order = 0.0;
		}
		
		public PlayCardsAction(Player p, double order) {
			singlePlayerToExecute = p;
			this.order = order;
		}
		
		@Override
		public double getOrder() {
			return order;
		}

		@Override
		public String getName() {
			return "playCards";
		}

		@Override
		public ActionResponse execute(Game game) {
			log.info("executing PlayCardsAction");
			if (singlePlayerToExecute != null) {
				game.removePostTurnAction(singlePlayerToExecute, getClass());
				singlePlayerToExecute.playCard(game);
				return null;
			}
			
			for (Player p : players) {
				p.playCard(game);
			}
			return null;
		}
		
	}
	
	public class ResolveCommerceAction implements NonPlayerAction, PostTurnAction {

		private Player singlePlayerToExecute;
		private double order;

		public ResolveCommerceAction() {
			order = 0.1;
		}
		
		public ResolveCommerceAction(Player p) {
			singlePlayerToExecute = p;
			order = 0.1;
		}
		
		@Override
		public double getOrder() {
			return order;
		}

		@Override
		public String getName() {
			return "resolveCommerce";
		}

		@Override
		public ActionResponse execute(Game game) {
			log.info("executing ResolveCommerceAction");
			
			if (singlePlayerToExecute != null) {
				game.removePostTurnAction(singlePlayerToExecute, getClass());
				singlePlayerToExecute.resolveCommerce();
				return null;
			}

			for (Player p : players) {
				p.resolveCommerce();
			}
			return null;
		}
		
	}
	
	public class DiscardFinalCardAction implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {
			return 1;
		}

		@Override
		public String getName() {
			return "discardFinal";
		}

		@Override
		public ActionResponse execute(Game game) {
			if (!game.ageIsStarted.get() || !ages.isFinalTurn()) {
				return null;
			}
			
			for (Player p : players) {
				p.discard(discard);
			}
			return null;
		}
		
	}
	
	public class ResolveConflictAction implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {
			return 2;
		}

		@Override
		public String getName() {
			return "resolveConflict";
		}

		@Override
		public ActionResponse execute(Game game) {
			
			if (!game.ageIsStarted.get() || !ages.isFinalTurn()) {
				return null;
			}

			int thisAgeVictory;
			if (ages.getCurrentAge() == 1) thisAgeVictory = 1;
			else if (ages.getCurrentAge() == 2) thisAgeVictory = 3;
			else if (ages.getCurrentAge() == 3) thisAgeVictory = 5;
			else throw new RuntimeException("invalid age found");
			
			
			for(Player p : players) {
				Player left = getLeftOf(p);
				
				int myArmy = p.getArmies();
				int leftArmy = left.getArmies();
				
				if (myArmy < leftArmy) {
					p.addDefeat(ages.getCurrentAge());
					left.addVictory(ages.getCurrentAge(), thisAgeVictory);
				}
				else if (myArmy > leftArmy) {
					p.addVictory(ages.getCurrentAge(), thisAgeVictory);
					left.addDefeat(ages.getCurrentAge());
				}			
			}

			return null;
		}
		
	}
	
	public enum Expansions {
		LEADERS,
		CITIES
	}

	public boolean allWaiting() {
		return players.allWaiting();
	}

	public boolean hasNextPhase() {
		return phases.hasNext();
	}
	
	public boolean phaseComplete() {
		return phases.phaseComplete(this);
	}

	public boolean hasPostTurnActions() {
		return postTurnActions.hasNext();
	}

	public boolean hasPostGameActions() {
		return postGameActions.hasNext();
	}

	public boolean isAgeStarted() {
		return this.ageIsStarted.get();
	}
}
