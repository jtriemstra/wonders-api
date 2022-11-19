package com.jtriemstra.wonders.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerArmyFacade {
	private List<Integer> shields;
	private Map<Integer, List<MilitaryResult>> defeats;
	private Map<Integer, List<MilitaryResult>> victories;
	
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

	public void addDefeat(int age, boolean isLeft, String neighborName) {
		if (!defeats.containsKey(age)) {
			defeats.put(age, new ArrayList<>());
		}
		defeats.get(age).add(new MilitaryResult(isLeft, neighborName, -1));
	}

	public void addVictory(int age, int points, boolean isLeft, String neighborName) {
		if (!victories.containsKey(age)) {
			victories.put(age, new ArrayList<>());
		}
		victories.get(age).add(new MilitaryResult(isLeft, neighborName, points));
	}
	
	public int getNumberOfDefeats() {
		return defeats.values().stream().mapToInt(l -> l.size()).reduce(0, Integer::sum);
	}
	
	public Map<Integer, List<Integer>> getVictories() {
		// TODO: return more details?
		Map<Integer, List<Integer>> result = new HashMap<>();
		victories.forEach((i,l) -> {
			List<Integer> values = new ArrayList<>();
			result.put(i, values);
			l.forEach(v -> values.add(v.getPoints()));
		});
		return result;
	}
		
	public List<MilitaryResult> getDefeats(int age) {
		return (defeats == null || defeats.get(age) == null) ? new ArrayList<>() : defeats.get(age);
	}
	
	public List<MilitaryResult> getVictories(int age) {
		return (victories == null || victories.get(age) == null) ? new ArrayList<>() : victories.get(age);
	}
}
