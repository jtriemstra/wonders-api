package com.jtriemstra.wonders.api.model;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jtriemstra.wonders.api.model.action.GetEndOfAge;
import com.jtriemstra.wonders.api.model.action.GetEndOfGame;
import com.jtriemstra.wonders.api.model.action.NonPlayerAction;
import com.jtriemstra.wonders.api.model.action.PostTurnAction;
import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.action.Wait;
import com.jtriemstra.wonders.api.model.action.Wait.For;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.dto.response.ActionResponse;
import com.jtriemstra.wonders.api.model.Player;
import com.jtriemstra.wonders.api.model.Game.DiscardFinalCardAction;
import com.jtriemstra.wonders.api.model.Game.PlayCardsAction;
import com.jtriemstra.wonders.api.model.Game.ResolveCommerceAction;
import com.jtriemstra.wonders.api.model.Game.ResolveConflictAction;

public class Game {
	private String name;
	private int numberOfPlayersExpected=3;
	
	private BoardFactory boards;
	private Ages ages;
	private AtomicBoolean ageIsStarted = new AtomicBoolean(false);
	private DeckFactory deckFactory;
	private PostTurnActions postTurnActions;
	private PostTurnActions postGameActions;
	
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

	public String getName() {
		return name;
	}

	public int getCurrentAge() {
		return ages.getCurrentAge();
	}
	
	public int getNumberOfPlayers() {
		return players.size();
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayersExpected = numberOfPlayers;
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
		p.addNextAction(new Wait(For.START));

		p.setBoard(boards.getBoard());
	}
	
	//returns a boolean indicating whether or not to pop the Wait action off the stack, i.e. if the Player can now stop waiting
	public boolean notifyWaiting(For waitFor) {
		
		if (waitFor == Wait.For.PLAYERS) {
			return players.size() == this.numberOfPlayersExpected;
		}
		
		if (waitFor == Wait.For.START) {
			return ages.getCurrentAge() > 0;
		}
		
		//TODO: (low) the post game actions could be done simultaneously, rather than sequentially
		synchronized (this) {
			if (players.allWaiting()) {
				
				if (postTurnActions.hasNext()) {
					postTurnActions.doNext();
				}
				else {
					if (ages.isFinalAge() && ages.isFinalTurn() && postGameActions.hasNext()) {
						postGameActions.doNext();						
					}
					else {
						postTurnActions.cleanUp();
						moveToNextTurnOrEndGame();
					}
				}
				
				// this goes hand-in-hand with the note above pushing a Wait.For.TURN onto the queue - for now, don't want to pop that off
				return false;
			}		
		}
		
		return false;
	}

	public void startGame() {
		if (this.numberOfPlayersExpected != this.players.size()) {
			throw new RuntimeException("still waiting for players");
		}
		
		startAge();
		
		for (Player p : players) {
			p.popAction();
			// not sure this is necessary, but just sits on the queue as a fallback next action.
			p.addNextAction(new Wait(Wait.For.TURN));
			p.startTurn();
		}
	}
	
	public void startAge() {
		if (!ageIsStarted.getAndSet(true)) {
			ages.incrementAge();
					
			dealCards(ages.getCurrentAge());
		}			
	}	
	
	private void dealCards(int age) {
		Deck deck = deckFactory.getDeck(players.size(), age);
		
		for (int i=0; i<7; i++) {
			for (Player p : players) {
				p.receiveCard(deck.draw());
			}			
		}
	}
	
	private void moveToNextTurnOrEndGame() {
		
		//TODO: (low) this could probably be cleaned up - maybe move into the PostTurnAction / PostGameAction structure
		if (ages.finishTurnAndCheckEndOfAge()) {
			//returning true means the age is complete
			
			ageIsStarted.set(false);
			
			if (!ages.isFinalAge()) {
				for (Player p : players) {
					p.addNextAction(new GetEndOfAge());
				}	
			}	
			else {
				for (Player p : players) {
					p.addNextAction(new GetEndOfGame());
				}
			}
			
		}
		else {
			if (ages.passClockwise()) {
				players.passCardsClockwise();
			}
			else {
				players.passCardsCounterClockwise();
			}
			
			for (Player p : players) {
				p.startTurn();
			}
		}
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
}
