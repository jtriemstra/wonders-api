package com.jtriemstra.wonders.api.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.jtriemstra.wonders.api.model.board.Board;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.phases.GameFlow;
import com.jtriemstra.wonders.api.model.phases.Phase;
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
		
	private GameFlow gameFlow;
	private DiscardPile discard;
	private PlayerList players;
		
	@Getter @Setter
	private int initialCoins = 3;	
	
	@Getter @Setter
	private PointCalculationStrategy defaultCalculation = () -> new VictoryPointFacade();
		
	private BoardManager boardManager;
		
	public Game(String name, 
			int numberOfPlayers,
			DiscardPile discard, 
			PlayerList players,
			GameFlow phases,
			BoardManager boardManager) {
		this.name = name;
		this.numberOfPlayersExpected = numberOfPlayers;
		this.discard = discard;
		this.players = players;
		this.boardManager = boardManager;
		this.gameFlow = phases;
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
		p.setPointCalculations(defaultCalculation.getPointFacade());
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
	
	public int[] getDiscardAges() {
		return discard.getAges();
	}
	
	


	
	
	
	public GameFlow getFlow() {
		return gameFlow;
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
		return gameFlow.hasNext();
	}
	
	public boolean isPhaseStarted() {
		return gameFlow.isPhaseStarted();
	}
	
	public boolean phaseComplete() {
		return gameFlow.phaseComplete();
	}

	public void startNextPhase() {
		gameFlow.nextPhase();
		gameFlow.phaseStart(this);
	}
	
	public void phaseLoop() {
		gameFlow.phaseLoop(this);
	}
	
	public void phaseEnd() {
		gameFlow.phaseEnd(this);
	}
	
	public Phase getCurrentPhase() {
		return gameFlow.getCurrentPhase();
	}

	public void removePlayers() {
		
	}
}
