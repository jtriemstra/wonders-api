package com.jtriemstra.wonders.api.model;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.action.BaseAction;
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.board.ChooseBoardFactory;
import com.jtriemstra.wonders.api.model.buildrules.BuildableRuleChain;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.deck.AgeDeck;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.exceptions.BoardInUseException;
import com.jtriemstra.wonders.api.model.phases.GamePhaseFactoryBasic;
import com.jtriemstra.wonders.api.model.phases.GamePhaseStart;
import com.jtriemstra.wonders.api.model.phases.Phases;
import com.jtriemstra.wonders.api.model.playrules.PlayableRuleChain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Game {
	@Getter
	private String name;
	@Getter @Setter
	private int numberOfPlayersExpected=3;
	
	private BoardFactory boards;
	private Ages ages;
	private AtomicBoolean ageIsStarted = new AtomicBoolean(false);
	@Setter
	private DeckFactory deckFactory;
	private PostTurnActions postTurnActions;
	private PostTurnActions postGameActions;
	
	@Getter @Setter
	private boolean isReady;

	//TODO: this only exists to support the ChooseBoard scenario, is there a better place to put it?
	@Getter @Setter
	private boolean defaultPlayerReady = true;
	//TODO: another thing that maybe should be created before the Game object, instead of setting in UpdateGame. defaulting here to make tests easier
	@Getter @Setter
	private Phases phases = new Phases(new GamePhaseFactoryBasic());
	
	@Autowired
	private DiscardPile discard;
	
	@Autowired
	private PlayerList players;
	
	
	public Game(String name, 
			BoardFactory boards, 
			Ages ages, 
			DeckFactory deckFactory,
			PostTurnActions postTurnActions,
			PostTurnActions postGameActions) {
		this.name = name;
		this.boards = boards;
		this.ages = ages;
		this.deckFactory = deckFactory;
		this.postTurnActions = postTurnActions;
		this.postGameActions = postGameActions;
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
		p.isReady(this.defaultPlayerReady);
		p.setBoard(boards.getBoard());
	}
	
	//TODO: unify approach with addPlayer - this ties into when players should get created, and when boards should get assigned, and when dependencies get injected into Game
	public void addFirstPlayer(Player p) {
		players.addPlayer(p);
		p.isReady(this.defaultPlayerReady);
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
	
	public Map<String, Boolean> getBoardsInUse() {
		if (!(boards instanceof ChooseBoardFactory)) {
			throw new RuntimeException("this game doesn't allow choosing boards");
		}
		
		return ((ChooseBoardFactory) boards).getBoardsInUse();
	}
	
	public Board boardSwap(int oldId, int newId, boolean sideA) {
		if (!(boards instanceof ChooseBoardFactory)) {
			throw new RuntimeException("this game doesn't allow choosing boards");
		}
		
		try {
			Board b = ((ChooseBoardFactory) boards).swap(oldId, newId, sideA);
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

		@Override
		public double getOrder() {
			return 0;
		}

		@Override
		public String getName() {
			return "playCards";
		}

		@Override
		public ActionResponse execute(Game game) {
			for (Player p : players) {
				p.playCard(game);
			}
			return null;
		}
		
	}
	
	public class ResolveCommerceAction implements NonPlayerAction, PostTurnAction {

		@Override
		public double getOrder() {
			return 0.1;
		}

		@Override
		public String getName() {
			return "resolveCommerce";
		}

		@Override
		public ActionResponse execute(Game game) {
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
			if (!ages.isFinalTurn()) {
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
			
			if (!ages.isFinalTurn()) {
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
	
	public enum BoardSide {
		A_ONLY,
		B_ONLY,
		A_OR_B
	}
	
	public enum Expansions {
		LEADERS,
		CITIES
	}

	public void setBoardFactory(BoardFactory boards) {
		// TODO: disallow if the configuration was set for NamedBoardFactory?
		this.boards = boards; 
	}

	public void setBoardSideOptions(BoardSide sideOptions) {
		this.boards.setSideOptions(sideOptions);
	}

	public Board getNextBoard() {
		return boards.getBoard();
	}

	public boolean allWaiting() {
		return players.allWaiting();
	}

	public boolean allReady() {
		for (Player p : players) {
			if (!p.isReady()) return false;
		}
		
		return true;
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
}
