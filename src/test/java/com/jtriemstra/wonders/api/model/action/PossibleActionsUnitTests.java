package com.jtriemstra.wonders.api.model.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PossibleActionsUnitTests {

	@Test
	public void when_creating_then_string_is_correct() {
		PossibleActions test = new PossibleActions(new WaitTurn(), new GetOptionsScience());
		
		Assertions.assertEquals("wait;options", test.toString());
	}

	@Test
	public void when_creating_then_can_get() {
		PossibleActions test = new PossibleActions(new WaitTurn(), new GetOptionsScience());
		
		Assertions.assertNotNull(test.getByName("wait"));
	}

	@Test
	public void when_creating_then_can_remove() {
		PossibleActions test = new PossibleActions(new WaitTurn(), new GetOptionsScience());
		
		test.removeAction("wait");
	}

	@Test
	public void when_creating_then_can_includes() {
		PossibleActions test = new PossibleActions(new WaitTurn(), new GetOptionsScience());
		
		test.includes("wait");
	}
}
