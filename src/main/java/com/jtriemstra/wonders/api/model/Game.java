package com.jtriemstra.wonders.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	@JsonProperty("discard")
	private DiscardPile discard;
	@JsonProperty("players")
	private PlayerList players;
		
	@Getter @Setter
	private int initialCoins = 3;	
	
	@Getter @Setter
	private PointCalculationStrategy defaultCalculation = () -> new VictoryPointFacade();
	
	@JsonProperty("boardManager")
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
		
	public IPlayer getLeftOf(IPlayer player) {
		return players.getLeftOf(player);
	}

	public IPlayer getRightOf(IPlayer player) {
		return players.getRightOf(player);
	}
	
	public IPlayer getPlayer(String name) {
		return players.getPlayer(name);
	}
	
	public void addPlayer(IPlayer p) {
		players.addPlayer(p);
		Board b = boardManager.createNextBoard();
		p.setBoard(b);		
		p.setPointCalculations(defaultCalculation.getPointFacade());
	}
	
	public void doForEachPlayer(PlayerLoop action) {
		for (IPlayer p : players) {
			action.execute(p);
		}
	}
	
	public interface PlayerLoop {
		public void execute(IPlayer p);
	}
	
	
	
	
	
	
	
	public void addToDiscard(Card c) {
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
