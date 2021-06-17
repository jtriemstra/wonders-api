package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jtriemstra.wonders.api.model.Player;

@Component
@Scope("prototype")
public class PlayerList implements Iterable<Player> {
	private List<Player> players;
	private HashMap<String, Player> playersByName = new HashMap<>();
	
	public PlayerList() {
		players = new ArrayList<>();
	}
	
	public void addPlayer(Player p) {
		players.add(p);
		playersByName.put(p.getName(), p);
	}
	
	public int size() {
		return players.size();
	}
	
	@Override
	public Iterator<Player> iterator() {
		return players.iterator();
	}
	
	public Player getPlayer(String name) {
		return playersByName.get(name);
	}
	
	public Player getLeftOf(Player player) {
		int index = players.indexOf(player);
		if (index == 0) {
			return players.get(players.size() - 1);
		}
		return players.get(index - 1);
	}

	public Player getRightOf(Player player) {
		int index = players.indexOf(player);
		if (index == players.size() - 1) {
			return players.get(0);
		}
		return players.get(index + 1);
	}
	
	public boolean allWaiting() {
		for (Player p : players) {
			if (!p.getNextAction().includes("wait")) {
				return false;
			}
		}
		
		return true;
	}
	
	public void passCardsCounterClockwise() {
		CardList c = players.get(0).getHand();
		for (int i=0; i<players.size()-1; i++) {
			players.get(i).setHand(players.get(i+1).getHand());
		}
		players.get(players.size()-1).setHand(c);
	}

	public void passCardsClockwise() {
		CardList c = players.get(players.size()-1).getHand();
		for (int i=players.size() -1; i>0; i--) {
			players.get(i).setHand(players.get(i-1).getHand());
		}
		players.get(0).setHand(c);
	}
}
