package com.jtriemstra.wonders.api.model;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.model.action.PostTurnActions;
import com.jtriemstra.wonders.api.model.board.BoardManager;
import com.jtriemstra.wonders.api.model.deck.DeckFactory;
import com.jtriemstra.wonders.api.model.phases.GameFlow;

public class GameListUnitTests {
	
	private Game newGame() {
		return new Game("asdf", 3, Mockito.mock(Ages.class), Mockito.mock(PostTurnActions.class), Mockito.mock(PostTurnActions.class), Mockito.mock(DiscardPile.class), Mockito.mock(PlayerList.class), Mockito.mock(GameFlow.class), Mockito.mock(BoardManager.class));
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
		
		Set<String> s = gl.getGames();
		Assertions.assertNotNull(s);
		Assertions.assertEquals(1, s.size());
		Assertions.assertEquals("asdf", s.iterator().next());
	}
	
}
