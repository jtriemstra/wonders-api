package com.jtriemstra.wonders.api.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerArmyFacadeUnitTests {

	@Test
	public void when_adding_victory_then_appears_in_results() {
		PlayerArmyFacade testObject = new PlayerArmyFacade();
		testObject.addVictory(1, 1, false, "dummy name");
		
		Assertions.assertEquals(1, testObject.getVictories().size());
		Assertions.assertEquals(1, testObject.getVictories().get(1).get(0));
		Assertions.assertEquals(0, testObject.getNumberOfDefeats());
		Assertions.assertEquals(1, testObject.getVictories(1).size());
		Assertions.assertEquals(1, testObject.getVictories(1).get(0).getPoints());
	}

	@Test
	public void when_adding_defeat_then_appears_in_results() {
		PlayerArmyFacade testObject = new PlayerArmyFacade();
		testObject.addDefeat(1, false, "dummy name");
		
		Assertions.assertEquals(0, testObject.getVictories().size());
		Assertions.assertEquals(1, testObject.getNumberOfDefeats());
		Assertions.assertEquals(1, testObject.getDefeats(1).size());
		Assertions.assertEquals(-1, testObject.getDefeats(1).get(0).getPoints());
	}
}
