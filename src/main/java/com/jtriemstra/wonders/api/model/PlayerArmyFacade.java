package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jtriemstra.wonders.api.model.card.provider.VictoryPointProvider;

public class PlayerArmyFacade {
	private List<Integer> shields;
	private Map<Integer, List<Integer>> defeats;
	private Map<Integer, List<Integer>> victories;
	
	public PlayerArmyFacade() {
		this.defeats = new HashMap<>();
		this.victories = new HashMap<>();
		this.shields = new ArrayList<>();		
	}

	public void addShields(int shields) {
		this.shields.add(shields);
	}
	
	public int getArmies() {
		return shields.stream().mapToInt(Integer::intValue).sum();
	}

	public void addDefeat(int age) {
		if (!defeats.containsKey(age)) {
			defeats.put(age, new ArrayList<>());
		}
		defeats.get(age).add(-1);
	}

	public void addVictory(int age, int victory) {
		if (!victories.containsKey(age)) {
			victories.put(age, new ArrayList<>());
		}
		victories.get(age).add(victory);
	}
	
	public int getNumberOfDefeats() {
		return defeats.values().stream().mapToInt(l -> l.size()).reduce(0, Integer::sum);
	}
	
	public Map<Integer, List<Integer>> getVictories() {
		return victories;
	}
		
	public int getNumberOfDefeats(int age) {
		return (defeats == null || defeats.get(age) == null) ? 0 : defeats.get(age).size();
	}
	
	public int getNumberOfVictories(int age) {
		return (victories == null || victories.get(age) == null) ? 0 : victories.get(age).size();
	}
	
}
