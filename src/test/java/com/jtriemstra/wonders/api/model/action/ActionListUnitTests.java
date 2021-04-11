package com.jtriemstra.wonders.api.model.action;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ActionListUnitTests {
	
	@Test
	public void when_getting_action_throws_exception_on_empty_list() {
		ActionList actionList = new ActionList();
		
		Throwable exceptionThrown = Assertions.assertThrows(RuntimeException.class, () -> {
			actionList.getNext();
		});
		
		assertEquals("tried to get next action, but there are zero actions available", exceptionThrown.getMessage());
	}
	
	@Test
	public void when_adding_action_can_get() {
		ActionList actionList = new ActionList();
		actionList.push(new WaitBoards());
		
		Assertions.assertNotNull(actionList.getNext());
		Assertions.assertNotNull(actionList.getCurrentByName("wait"));
	}
}
