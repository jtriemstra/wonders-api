package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jtriemstra.wonders.api.model.Player;

@Component
@Scope("prototype")
public class PlayerList implements Iterable<IPlayer> {
	@JsonProperty("players")
	private List<IPlayer> players;
	@JsonProperty("playersByName")
	private HashMap<String, IPlayer> playersByName = new HashMap<>();
	
	public PlayerList() {
		players = new ArrayList<>();
	}
	
	public void addPlayer(IPlayer p) {
		players.add(p);
		playersByName.put(p.getName(), p);
	}
	
	public int size() {
		return players.size();
	}
	
	@Override
	public Iterator<IPlayer> iterator() {
		return players.iterator();
	}
	
	public IPlayer getPlayer(String name) {
		return playersByName.get(name);
	}
	
	private int getIndex(IPlayer player) {
		for (int index=0; index<players.size(); index++) {
			if (player.getName().equals(players.get(index).getName())) {
				return index;
			}
		}
		
		throw new RuntimeException("player not found");
	}
	public IPlayer getLeftOf(IPlayer player) {
		int index = getIndex(player);
		if (index == 0) {
			return players.get(players.size() - 1);
		}
		return players.get(index - 1);
	}

	public IPlayer getRightOf(IPlayer player) {
		int index = getIndex(player);
		if (index == players.size() - 1) {
			return players.get(0);
		}
		return players.get(index + 1);
	}
	
	public boolean allWaiting() {
		for (IPlayer p : players) {
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
