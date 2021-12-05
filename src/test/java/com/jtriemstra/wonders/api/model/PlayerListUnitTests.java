package com.jtriemstra.wonders.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jtriemstra.wonders.api.model.action.ActionList;
import com.jtriemstra.wonders.api.model.leaders.PlayerLeaders;

public class PlayerListUnitTests {
	
	private PlayerList playerList;
	
	private Player getMockPlayer(String name) {
		Player mock = Mockito.mock(Player.class);
		Mockito.when(mock.getName()).thenReturn(name);
		return mock;
	}
	
	@BeforeEach //it looks like the PlayerList is re-created and re-injected for each test
	public  void setup() {
		playerList = new PlayerList();
		ActionList a = Mockito.mock(ActionList.class);
		playerList.addPlayer(getMockPlayer("test1"));
		playerList.addPlayer(getMockPlayer("test2"));
		playerList.addPlayer(getMockPlayer("test3"));
		playerList.addPlayer(getMockPlayer("test4"));
		playerList.addPlayer(getMockPlayer("test5"));
		playerList.addPlayer(getMockPlayer("test6"));
		
	}
	
	@Test
	public void when_getting_left_returns_correctly() {
		assertEquals(6, playerList.size());
		IPlayer p3 = playerList.getPlayer("test3");
		assertEquals("test2", playerList.getLeftOf(p3).getName());
	}
	
	@Test
	public void when_getting_left_wraps_around_list_correctly() {
		assertEquals(6, playerList.size());
		IPlayer p1 = playerList.getPlayer("test1");
		assertEquals("test6", playerList.getLeftOf(p1).getName());
	}

	@Test
	public void when_getting_right_wraps_around_list_correctly() {
		assertEquals(6, playerList.size());
		IPlayer p6 = playerList.getPlayer("test6");
		assertEquals("test1", playerList.getRightOf(p6).getName());
	}
	
	@Test
	public void when_using_leaders_getting_left_returns_correctly() {
		PlayerList x = new PlayerList();
		IPlayer y = new PlayerLeaders(getMockPlayer("test2"));
		x.addPlayer(new PlayerLeaders(getMockPlayer("test1")));
		x.addPlayer(y);
		x.addPlayer(new PlayerLeaders(getMockPlayer("test3")));
		
		assertEquals("test1", playerList.getLeftOf(y).getName());
	}
}
