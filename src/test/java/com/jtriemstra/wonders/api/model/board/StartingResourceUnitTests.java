package com.jtriemstra.wonders.api.model.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jtriemstra.wonders.api.model.resource.ResourceType;

public class StartingResourceUnitTests {
	@Test
	public void when_board_is_ephesus_start_with_paper() {
		Board b1 = new Ephesus(true);
		Board b2 = new Ephesus(false);
		
		Assertions.assertEquals(b1.getStartingResource().getSingle(), ResourceType.PAPER);
		Assertions.assertEquals(b2.getStartingResource().getSingle(), ResourceType.PAPER);
	}
	
	@Test
	public void when_board_is_halikarnassos_start_with_loom() {
		Board b1 = new Halikarnassos(true);
		Board b2 = new Halikarnassos(false);
		
		Assertions.assertEquals(b1.getStartingResource().getSingle(), ResourceType.TEXTILE);
		Assertions.assertEquals(b2.getStartingResource().getSingle(), ResourceType.TEXTILE);
	}
	
	@Test
	public void when_board_is_babylon_start_with_brick() {
		Board b1 = new Babylon(true);
		Board b2 = new Babylon(false);
		
		Assertions.assertEquals(b1.getStartingResource().getSingle(), ResourceType.BRICK);
		Assertions.assertEquals(b2.getStartingResource().getSingle(), ResourceType.BRICK);
	}
	
	@Test
	public void when_board_is_rhodes_start_with_ore() {
		Board b1 = new Rhodes(true);
		Board b2 = new Rhodes(false);
		
		Assertions.assertEquals(b1.getStartingResource().getSingle(), ResourceType.ORE);
		Assertions.assertEquals(b2.getStartingResource().getSingle(), ResourceType.ORE);
	}
	
	@Test
	public void when_board_is_olympia_start_with_wood() {
		Board b1 = new Olympia(true);
		Board b2 = new Olympia(false);
		
		Assertions.assertEquals(b1.getStartingResource().getSingle(), ResourceType.WOOD);
		Assertions.assertEquals(b2.getStartingResource().getSingle(), ResourceType.WOOD);
	}
	
	@Test
	public void when_board_is_alexandria_start_with_glass() {
		Board b1 = new Alexandria(true);
		Board b2 = new Alexandria(false);
		
		Assertions.assertEquals(b1.getStartingResource().getSingle(), ResourceType.GLASS);
		Assertions.assertEquals(b2.getStartingResource().getSingle(), ResourceType.GLASS);
	}
	
	@Test
	public void when_board_is_gize_start_with_stone() {
		Board b1 = new Giza(true);
		Board b2 = new Giza(false);
		
		Assertions.assertEquals(b1.getStartingResource().getSingle(), ResourceType.STONE);
		Assertions.assertEquals(b2.getStartingResource().getSingle(), ResourceType.STONE);
	}
}
