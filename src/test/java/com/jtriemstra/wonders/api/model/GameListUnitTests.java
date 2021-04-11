package com.jtriemstra.wonders.api.model;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardFactory;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;

public class GameListUnitTests {
	
	private Game newGame() {
		return new Game("asdf", Mockito.mock(BoardFactory.class), Mockito.mock(Ages.class), Mockito.mock(DeckFactory.class), Mockito.mock(PostTurnActions.class), Mockito.mock(PostTurnActions.class));
	}
	
	@Test
	public void when_adding_game_can_get_by_name() {
		GameList gl = new GameList();
		Game g = newGame();
		gl.add("asdf", g);
		
		Assertions.assertNotNull(gl.get("asdf"));
	}
	
	@Test
	public void when_adding_game_can_get_name_set() {
		GameList gl = new GameList();
		Game g = newGame();
		gl.add("asdf", g);
		g.setReady(true);
		
		Set<String> s = gl.getGames();
		Assertions.assertNotNull(s);
		Assertions.assertEquals(1, s.size());
		Assertions.assertEquals("asdf", s.iterator().next());
	}

	@Test
	public void when_adding_game_invisible_until_ready() {
		GameList gl = new GameList();
		Game g = newGame();
		gl.add("asdf", g);
		
		Set<String> s = gl.getGames();
		Assertions.assertNotNull(s);
		Assertions.assertEquals(0, s.size());
	}
	
}
