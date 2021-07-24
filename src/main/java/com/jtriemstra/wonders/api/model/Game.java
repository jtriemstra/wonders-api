package com.jtriemstra.wonders.api.model;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.phases.Phases;
import com.jtriemstra.wonders.api.model.points.VictoryPointFacade;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Game {
	
	public enum Expansions {
		LEADERS,
		CITIES
	}
	
	@Getter
	private String name;
	
	@Getter 
	private int numberOfPlayersExpected;
		
	private PostTurnActions postTurnActions;
	private PostTurnActions postGameActions;
	
	private Phases phases;
	
	private DiscardPile discard;

	private PlayerList players;
		
	@Getter @Setter
	private int initialCoins = 3;	
	
	@Getter @Setter
	private PointCalculationStrategy defaultCalculation = () -> new VictoryPointFacade();
		
	private BoardManager boardManager;
		
	public Game(String name, 
			int numberOfPlayers,
			PostTurnActions postTurnActions,
			PostTurnActions postGameActions, 
			DiscardPile discard, 
			PlayerList players,
			Phases phases,
			BoardManager boardManager) {
		this.name = name;
		this.numberOfPlayersExpected = numberOfPlayers;
		this.postTurnActions = postTurnActions;
		this.postGameActions = postGameActions;
		this.discard = discard;
		this.players = players;
		this.boardManager = boardManager;
		this.phases = phases;
	}
	
	@PostConstruct
	public void postConstruct() {
		postTurnActions.setGame(this);
		postGameActions.setGame(this);
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
	
	public void addPlayer(Player p) {
		players.addPlayer(p);
		Board b = boardManager.getBoard();
		p.setBoard(b);		
	}
	
	public void doForEachPlayer(PlayerLoop action) {
		for (Player p : players) {
			action.execute(p);
		}
	}
	
	public interface PlayerLoop {
		public void execute(Player p);
	}
	
	
	
	
	
	
	
	public void discard(Card c) {
		discard.add(c);
	}

	public Card removeFromDiscard(String cardName) {
		return discard.remove(cardName);
	}

	public Card[] getDiscardCards() {
		return discard.getCards();
	}
	
	
	
	

	public void addPostTurnAction(Player p, PostTurnAction action) {
		postTurnActions.add(p, action);
	}
	
	public void injectPostTurnAction(Player p, PostTurnAction action, int additionalIndex) {
		postTurnActions.inject(p, action, additionalIndex);
	}

	public void addPostGameAction(Player p, PostTurnAction action) {
		postGameActions.add(p, action);
	}

	public void removePostTurnAction(Player player, Class clazz) {
		postTurnActions.markForRemoval(player, clazz);
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
			if (isFinalAge() && isFinalTurn() && postGameActions.hasNext()) {
				postGameActions.doNext();						
			}
		}		
	}
	
	public void cleanUpPostTurn() {
		postTurnActions.cleanUp();
	}

	public boolean hasPostTurnActions() {
		return postTurnActions.hasNext();
	}

	public boolean hasPostGameActions() {
		return postGameActions.hasNext();
	}
	
	

	public boolean isFinalTurn() {
		return phases.isFinalTurn();
	}
	
	public boolean isFinalAge() {
		return phases.isFinalAge();
	}

	public boolean isAgeStarted() {
		return phases.isAgeStarted();
	}

	public int getCurrentAge() {
		return phases.getCurrentAge();
	}
	
	
	
	
	
	public void passCards(boolean clockwise) {
		if (clockwise) {
			players.passCardsClockwise();
		}
		else {
			players.passCardsCounterClockwise();
		}
	}
	
	
	
	
	
	
	public boolean allWaiting() {
		return players.allWaiting();
	}

	public boolean hasNextPhase() {
		return phases.hasNext();
	}
	
	public boolean isPhaseStarted() {
		return phases.isPhaseStarted();
	}
	
	public boolean phaseComplete() {
		return phases.phaseComplete(this);
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
}
